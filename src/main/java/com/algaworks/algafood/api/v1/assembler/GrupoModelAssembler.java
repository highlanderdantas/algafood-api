package com.algaworks.algafood.api.v1.assembler;

import static com.algaworks.algafood.api.v1.AlgaLinks.linkToGrupoPermissoes;
import static com.algaworks.algafood.api.v1.AlgaLinks.linkToGrupos;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.controller.GrupoController;
import com.algaworks.algafood.api.v1.model.GrupoModel;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Grupo;

@Component
public class GrupoModelAssembler 
        extends RepresentationModelAssemblerSupport<Grupo, GrupoModel> {

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private AlgaSecurity algaSecurity;  
    
    public GrupoModelAssembler() {
        super(GrupoController.class, GrupoModel.class);
    }
    
    @Override
    public GrupoModel toModel(Grupo grupo) {
        GrupoModel grupoModel = createModelWithId(grupo.getId(), grupo);
        modelMapper.map(grupo, grupoModel);
        
        if (algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
            grupoModel.add(linkToGrupos("grupos"));
            
            grupoModel.add(linkToGrupoPermissoes(grupo.getId(), "permissoes"));
        }
        
        return grupoModel;
    }

    @Override
    public CollectionModel<GrupoModel> toCollectionModel(Iterable<? extends Grupo> entities) {
        CollectionModel<GrupoModel> collectionModel = super.toCollectionModel(entities);
        
        if (algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
            collectionModel.add(linkToGrupos());
        }
        
        return collectionModel;
    }         
}        