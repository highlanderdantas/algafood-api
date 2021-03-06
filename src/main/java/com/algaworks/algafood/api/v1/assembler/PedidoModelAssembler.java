package com.algaworks.algafood.api.v1.assembler;

import static com.algaworks.algafood.api.v1.AlgaLinks.*;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.controller.PedidoController;
import com.algaworks.algafood.api.v1.model.PedidoModel;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.core.security.AlgaSecurity;

@Component
public class PedidoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoModel>{

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
	private AlgaSecurity algaSecurity;
    
    public PedidoModelAssembler() {
    	super(PedidoController.class, PedidoModel.class);
    }
    
    @Override
    public PedidoModel toModel(Pedido pedido) {
        PedidoModel pedidoModel = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoModel);
        
        // Não usei o método algaSecurity.podePesquisarPedidos(clienteId, restauranteId) aqui,
        // porque na geração do link, não temos o id do cliente e do restaurante, 
        // então precisamos saber apenas se a requisição está autenticada e tem o escopo de leitura
        if (algaSecurity.podePesquisarPedidos()) {
            pedidoModel.add(linkToPedidos("pedidos"));
        }
        
        if (algaSecurity.podeGerenciarPedidos(pedido.getCodigo())) {
            if (pedido.podeSerConfirmado()) {
                pedidoModel.add(linkToConfirmacaoPedido(pedido.getCodigo(), "confirmar"));
            }
            
            if (pedido.podeSerCancelado()) {
                pedidoModel.add(linkToCancelamentoPedido(pedido.getCodigo(), "cancelar"));
            }
            
            if (pedido.podeSerEntregue()) {
                pedidoModel.add(linkToEntregaPedido(pedido.getCodigo(), "entregar"));
            }
        }
        
        if (algaSecurity.podeConsultarRestaurantes()) {
            pedidoModel.getRestaurante().add(
                    linkToRestaurante(pedido.getRestaurante().getId()));
        }
        
        if (algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
            pedidoModel.getCliente().add(
                    linkToUsuario(pedido.getCliente().getId()));
        }
        
        if (algaSecurity.podeConsultarFormasPagamento()) {
            pedidoModel.getFormaPagamento().add(
                    linkToFormaPagamento(pedido.getFormaPagamento().getId()));
        }
        
        if (algaSecurity.podeConsultarCidades()) {
            pedidoModel.getEnderecoEntrega().getCidade().add(
                    linkToCidade(pedido.getEnderecoEntrega().getCidade().getId()));
        }
        
        // Quem pode consultar restaurantes, também pode consultar os produtos dos restaurantes
        if (algaSecurity.podeConsultarRestaurantes()) {
            pedidoModel.getItens().forEach(item -> {
                item.add(linkToProduto(
                        pedidoModel.getRestaurante().getId(), item.getProdutoId(), "produto"));
            });
        }
        
        return pedidoModel;
    }
    
}