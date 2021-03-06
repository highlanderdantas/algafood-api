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
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteModelAssembler 
        extends RepresentationModelAssemblerSupport<Restaurante, RestauranteModel> {

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private AlgaSecurity algaSecurity;  
    
    public RestauranteModelAssembler() {
        super(RestauranteController.class, RestauranteModel.class);
    }
    
    @Override
    public RestauranteModel toModel(Restaurante restaurante) {
        RestauranteModel restauranteModel = createModelWithId(restaurante.getId(), restaurante);
        modelMapper.map(restaurante, restauranteModel);
        
        if (algaSecurity.podeConsultarRestaurantes()) {
            restauranteModel.add(linkToRestaurantes("restaurantes"));
        }
        
        if (algaSecurity.podeGerenciarCadastroRestaurantes()) {
            if (restaurante.ativacaoPermitida()) {
                restauranteModel.add(
                        linkToRestauranteAtivacao(restaurante.getId(), "ativar"));
            }

            if (restaurante.inativacaoPermitida()) {
                restauranteModel.add(
                        linkToRestauranteInativacao(restaurante.getId(), "inativar"));
            }
        }
        
        if (algaSecurity.podeGerenciarFuncionamentoRestaurantes(restaurante.getId())) {
            if (restaurante.aberturaPermitida()) {
                restauranteModel.add(
                        linkToRestauranteAbertura(restaurante.getId(), "abrir"));
            }

            if (restaurante.fechamentoPermitido()) {
                restauranteModel.add(
                        linkToRestauranteFechamento(restaurante.getId(), "fechar"));
            }
        }
        
        if (algaSecurity.podeConsultarRestaurantes()) {
            restauranteModel.add(linkToProdutos(restaurante.getId(), "produtos"));
        }
        
        if (algaSecurity.podeConsultarCozinhas()) {
            restauranteModel.getCozinha().add(
                    linkToCozinha(restaurante.getCozinha().getId()));
        }
        
        if (algaSecurity.podeConsultarCidades()) {
            if (restauranteModel.getEndereco() != null 
                    && restauranteModel.getEndereco().getCidade() != null) {
                restauranteModel.getEndereco().getCidade().add(
                        linkToCidade(restaurante.getEndereco().getCidade().getId()));
            }
        }
        
        if (algaSecurity.podeConsultarRestaurantes()) {
            restauranteModel.add(linkToRestauranteFormasPagamento(restaurante.getId(), 
                    "formas-pagamento"));
        }
        
        if (algaSecurity.podeGerenciarCadastroRestaurantes()) {
            restauranteModel.add(linkToRestauranteResponsaveis(restaurante.getId(), 
                    "responsaveis"));
        }
        
        return restauranteModel;
    }

    @Override
    public CollectionModel<RestauranteModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
        CollectionModel<RestauranteModel> collectionModel = super.toCollectionModel(entities);
        
        if (algaSecurity.podeConsultarRestaurantes()) {
            collectionModel.add(linkToRestaurantes());
        }
        
        return collectionModel;
    }  
}