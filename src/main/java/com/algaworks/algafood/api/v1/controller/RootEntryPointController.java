package com.algaworks.algafood.api.v1.controller;

import static com.algaworks.algafood.api.v1.AlgaLinks.linkToCidades;
import static com.algaworks.algafood.api.v1.AlgaLinks.linkToCozinhas;
import static com.algaworks.algafood.api.v1.AlgaLinks.linkToEstados;
import static com.algaworks.algafood.api.v1.AlgaLinks.linkToEstatisticas;
import static com.algaworks.algafood.api.v1.AlgaLinks.linkToFormasPagamento;
import static com.algaworks.algafood.api.v1.AlgaLinks.linkToGrupos;
import static com.algaworks.algafood.api.v1.AlgaLinks.linkToPedidos;
import static com.algaworks.algafood.api.v1.AlgaLinks.linkToPermissoes;
import static com.algaworks.algafood.api.v1.AlgaLinks.linkToRestaurantes;
import static com.algaworks.algafood.api.v1.AlgaLinks.linkToUsuarios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.core.security.AlgaSecurity;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@RequestMapping(path = "/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointController {
	
	@Autowired
	private AlgaSecurity algaSecurity;   

	
	@GetMapping
	public RootEntryPointModel root() {
	    var rootEntryPointModel = new RootEntryPointModel();
	    
	    if (algaSecurity.podeConsultarCozinhas()) {
	        rootEntryPointModel.add(linkToCozinhas("cozinhas"));
	    }
	    
	    if (algaSecurity.podePesquisarPedidos()) {
	        rootEntryPointModel.add(linkToPedidos("pedidos"));
	    }
	    
	    if (algaSecurity.podeConsultarRestaurantes()) {
	        rootEntryPointModel.add(linkToRestaurantes("restaurantes"));
	    }
	    
	    if (algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
	        rootEntryPointModel.add(linkToGrupos("grupos"));
	        rootEntryPointModel.add(linkToUsuarios("usuarios"));
	        rootEntryPointModel.add(linkToPermissoes("permissoes"));
	    }
	    
	    if (algaSecurity.podeConsultarFormasPagamento()) {
	        rootEntryPointModel.add(linkToFormasPagamento("formas-pagamento"));
	    }
	    
	    if (algaSecurity.podeConsultarEstados()) {
	        rootEntryPointModel.add(linkToEstados("estados"));
	    }
	    
	    if (algaSecurity.podeConsultarCidades()) {
	        rootEntryPointModel.add(linkToCidades("cidades"));
	    }
	    
	    if (algaSecurity.podeConsultarEstatisticas()) {
	        rootEntryPointModel.add(linkToEstatisticas("estatisticas"));
	    }
	    
	    return rootEntryPointModel;
	}
	
	private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel> {
	}
	
}