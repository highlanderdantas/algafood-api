package com.algaworks.algafood.api.v1.assembler;

import static com.algaworks.algafood.api.v1.AlgaLinks.linkToPedidos;
import static com.algaworks.algafood.api.v1.AlgaLinks.linkToRestaurante;
import static com.algaworks.algafood.api.v1.AlgaLinks.linkToUsuario;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.controller.PedidoController;
import com.algaworks.algafood.api.v1.model.PedidoResumoModel;
import com.algaworks.algafood.domain.model.Pedido;

@Component
public class PedidoResumoModelAssembler 
	extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoModel>{

    @Autowired
    private ModelMapper modelMapper;
    
    public PedidoResumoModelAssembler() {
    	super(PedidoController.class, PedidoResumoModel.class);
    }
    
    @Override
    public PedidoResumoModel toModel(Pedido pedido) {
    	PedidoResumoModel pedidoResumoModel = createModelWithId(pedido.getCodigo(), pedido);
    	
        modelMapper.map(pedido, pedidoResumoModel);
        
        pedidoResumoModel.add(linkToPedidos("pedidos"));
        
        pedidoResumoModel.getRestaurante().add(
                linkToRestaurante(pedido.getRestaurante().getId()));

        pedidoResumoModel.getCliente().add(
        		linkToUsuario(pedido.getCliente().getId()));
        
        return pedidoResumoModel;
    }
    
   
}