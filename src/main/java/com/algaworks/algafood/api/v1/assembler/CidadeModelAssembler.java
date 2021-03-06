package com.algaworks.algafood.api.v1.assembler;

import static com.algaworks.algafood.api.v1.AlgaLinks.linkToCidades;
import static com.algaworks.algafood.api.v1.AlgaLinks.linkToEstado;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.controller.CidadeController;
import com.algaworks.algafood.api.v1.model.CidadeModel;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Cidade;

@Component
public class CidadeModelAssembler 
		extends RepresentationModelAssemblerSupport<Cidade, CidadeModel> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaSecurity algaSecurity;
	
	public CidadeModelAssembler() {
		super(CidadeController.class, CidadeModel.class);
	}
	
	@Override
	public CidadeModel toModel(Cidade cidade) {
	    CidadeModel cidadeModel = createModelWithId(cidade.getId(), cidade);
	    
	    modelMapper.map(cidade, cidadeModel);
	    
	    if (algaSecurity.podeConsultarCidades()) {
	        cidadeModel.add(linkToCidades("cidades"));
	    }
	    
	    if (algaSecurity.podeConsultarEstados()) {
	        cidadeModel.getEstado().add(linkToEstado(cidadeModel.getEstado().getId()));
	    }
	    
	    return cidadeModel;
	}

	@Override
	public CollectionModel<CidadeModel> toCollectionModel(Iterable<? extends Cidade> entities) {
	    CollectionModel<CidadeModel> collectionModel = super.toCollectionModel(entities);
	    
	    if (algaSecurity.podeConsultarCidades()) {
	        collectionModel.add(linkToCidades());
	    }
	    
	    return collectionModel;
	}
	
}