package com.algaworks.algafood.api.assembler;

import static com.algaworks.algafood.api.helper.AlgaLinks.*;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.controller.PedidoController;
import com.algaworks.algafood.api.model.PedidoModel;
import com.algaworks.algafood.domain.model.Pedido;

@Component
public class PedidoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoModel>{

    @Autowired
    private ModelMapper modelMapper;
    
    public PedidoModelAssembler() {
    	super(PedidoController.class, PedidoModel.class);
    }
    
    @Override
    public PedidoModel toModel(Pedido pedido) {
    	PedidoModel pedidoModel = createModelWithId(pedido.getCodigo(), pedido);
    	
        modelMapper.map(pedido, pedidoModel);
        var restauranteId = pedidoModel.getRestaurante().getId();
        
        
		pedidoModel.add(linkToPedidos("pedidos"));
		
		if (pedido.podeSerConfirmado()) {
			pedidoModel.add(linkToConfirmacaoPedido(pedido.getCodigo(), "confirmar"));
		}
		
		if (pedido.podeSerCancelado()) {
			pedidoModel.add(linkToCancelamentoPedido(pedido.getCodigo(), "cancelar"));
		}
		
		if (pedido.podeSerEntregue()) {
			pedidoModel.add(linkToEntregaPedido(pedido.getCodigo(), "entregar"));
		}
        
		pedidoModel.getRestaurante().add(
	            linkToRestaurante(restauranteId));
	    
	    pedidoModel.getCliente().add(
	            linkToUsuario(pedido.getCliente().getId()));
	    
	    pedidoModel.getFormaPagamento().add(
	            linkToFormaPagamento(pedido.getFormaPagamento().getId()));
	    
	    pedidoModel.getEnderecoEntrega().getCidade().add(
	            linkToCidade(pedido.getEnderecoEntrega().getCidade().getId()));
	    
	    pedidoModel.getItens().forEach(item -> {
	        item.add(linkToProduto(
	        		restauranteId, item.getProdutoId(), "produto"));
	    });
        
        
        return pedidoModel;
    }
    
}