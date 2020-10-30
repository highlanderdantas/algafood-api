package com.algaworks.algafood.api.v1.assembler;

import static com.algaworks.algafood.api.v1.AlgaLinks.linkToCidade;
import static com.algaworks.algafood.api.v1.AlgaLinks.linkToCozinha;
import static com.algaworks.algafood.api.v1.AlgaLinks.linkToProdutos;
import static com.algaworks.algafood.api.v1.AlgaLinks.linkToRestauranteAbertura;
import static com.algaworks.algafood.api.v1.AlgaLinks.linkToRestauranteAtivacao;
import static com.algaworks.algafood.api.v1.AlgaLinks.linkToRestauranteFechamento;
import static com.algaworks.algafood.api.v1.AlgaLinks.linkToRestauranteFormasPagamento;
import static com.algaworks.algafood.api.v1.AlgaLinks.linkToRestauranteInativacao;
import static com.algaworks.algafood.api.v1.AlgaLinks.linkToRestauranteResponsaveis;
import static com.algaworks.algafood.api.v1.AlgaLinks.linkToRestaurantes;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.controller.RestauranteController;
import com.algaworks.algafood.api.v1.model.RestauranteModel;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteModelAssembler 
        extends RepresentationModelAssemblerSupport<Restaurante, RestauranteModel> {

    @Autowired
    private ModelMapper modelMapper;
    
    
    public RestauranteModelAssembler() {
        super(RestauranteController.class, RestauranteModel.class);
    }
    
    @Override
    public RestauranteModel toModel(Restaurante restaurante) {
        RestauranteModel restauranteModel = createModelWithId(restaurante.getId(), restaurante);
        modelMapper.map(restaurante, restauranteModel);
        
        restauranteModel.add(linkToRestaurantes("restaurantes"));
        
        if (restaurante.ativacaoPermitida()) {
            restauranteModel.add(
                    linkToRestauranteAtivacao(restaurante.getId(), "ativar"));
        }

        if (restaurante.inativacaoPermitida()) {
            restauranteModel.add(
                    linkToRestauranteInativacao(restaurante.getId(), "inativar"));
        }

        if (restaurante.aberturaPermitida()) {
            restauranteModel.add(
                    linkToRestauranteAbertura(restaurante.getId(), "abrir"));
        }

        if (restaurante.fechamentoPermitido()) {
            restauranteModel.add(
                    linkToRestauranteFechamento(restaurante.getId(), "fechar"));
        }
        
        restauranteModel.add(linkToProdutos(restaurante.getId(), "produtos"));
        
        restauranteModel.getCozinha().add(
                linkToCozinha(restaurante.getCozinha().getId()));
        
        if (restauranteModel.getEndereco() != null 
                && restauranteModel.getEndereco().getCidade() != null) {
            restauranteModel.getEndereco().getCidade().add(
                    linkToCidade(restaurante.getEndereco().getCidade().getId()));
        }
        
        restauranteModel.add(linkToRestauranteFormasPagamento(restaurante.getId(), 
                "formas-pagamento"));
        
        restauranteModel.add(linkToRestauranteResponsaveis(restaurante.getId(), 
                "responsaveis"));
        
        return restauranteModel;
    }
    
    @Override
    public CollectionModel<RestauranteModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
        return super.toCollectionModel(entities)
                .add(linkToRestaurantes());
    }   
}