package com.algaworks.algafood.api.assembler;

import static com.algaworks.algafood.api.helper.AlgaLinks.linkToFotoProduto;
import static com.algaworks.algafood.api.helper.AlgaLinks.linkToProdutos;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.controller.RestauranteProdutoController;
import com.algaworks.algafood.api.model.ProdutoModel;
import com.algaworks.algafood.domain.model.Produto;

@Component
public class ProdutoModelAssembler 
        extends RepresentationModelAssemblerSupport<Produto, ProdutoModel> {

    @Autowired
    private ModelMapper modelMapper;
    
    public ProdutoModelAssembler() {
        super(RestauranteProdutoController.class, ProdutoModel.class);
    }
    
    @Override
    public ProdutoModel toModel(Produto produto) {
        ProdutoModel produtoModel = createModelWithId(
                produto.getId(), produto, produto.getRestaurante().getId());
        
        modelMapper.map(produto, produtoModel);
        
        produtoModel.add(linkToProdutos(produto.getRestaurante().getId(), "produtos"));

        produtoModel.add(linkToFotoProduto(
                produto.getRestaurante().getId(), produto.getId(), "foto"));
        
        return produtoModel;
    }   
    
}
