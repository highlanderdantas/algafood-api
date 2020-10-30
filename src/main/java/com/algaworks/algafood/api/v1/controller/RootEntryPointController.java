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

import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@RequestMapping(path = "/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointController {

	
	@GetMapping
	public RootEntryPointModel root() {
		var rootEntryPointModel = new RootEntryPointModel();
		
		rootEntryPointModel.add(linkToCozinhas("cozinhas"));
		rootEntryPointModel.add(linkToPedidos("pedidos"));
		rootEntryPointModel.add(linkToRestaurantes("restaurantes"));
		rootEntryPointModel.add(linkToGrupos("grupos"));
		rootEntryPointModel.add(linkToUsuarios("usuarios"));
		rootEntryPointModel.add(linkToPermissoes("permissoes"));
		rootEntryPointModel.add(linkToFormasPagamento("formas-pagamento"));
		rootEntryPointModel.add(linkToEstados("estados"));
		rootEntryPointModel.add(linkToCidades("cidades"));
		rootEntryPointModel.add(linkToEstatisticas("estatisticas"));
		
		return rootEntryPointModel;
	}
	
	private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel> {
	}
	
}