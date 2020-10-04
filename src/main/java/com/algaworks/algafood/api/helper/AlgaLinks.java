package com.algaworks.algafood.api.helper;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariable.VariableType;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;

import com.algaworks.algafood.api.controller.CidadeController;
import com.algaworks.algafood.api.controller.CozinhaController;
import com.algaworks.algafood.api.controller.EstadoController;
import com.algaworks.algafood.api.controller.FormaPagamentoController;
import com.algaworks.algafood.api.controller.PedidoController;
import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.controller.RestauranteProdutoController;
import com.algaworks.algafood.api.controller.RestauranteUsuarioResponsavelController;
import com.algaworks.algafood.api.controller.UsuarioController;
import com.algaworks.algafood.api.controller.UsuarioGrupoController;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AlgaLinks {
	
	
	public static final TemplateVariables PAGINACAO_VARIABLES = new TemplateVariables(
			new TemplateVariable("page", VariableType.REQUEST_PARAM),
			new TemplateVariable("size", VariableType.REQUEST_PARAM),
			new TemplateVariable("sort", VariableType.REQUEST_PARAM));
	
	public static Link linkToPedidos() {
		TemplateVariables filtroVariables = new TemplateVariables(
				new TemplateVariable("clienteId", VariableType.REQUEST_PARAM),
				new TemplateVariable("restauranteId", VariableType.REQUEST_PARAM),
				new TemplateVariable("dataCriacaoInicio", VariableType.REQUEST_PARAM),
				new TemplateVariable("dataCriacaoFim", VariableType.REQUEST_PARAM));
		
		String pedidosUrl = linkTo(PedidoController.class).toUri().toString();
		
		return new Link(UriTemplate.of(pedidosUrl, 
				PAGINACAO_VARIABLES.concat(filtroVariables)), "pedidos");
	}
	
	public static Link linkToRestaurante(Long restauranteId, String rel) {
	    return linkTo(methodOn(RestauranteController.class)
	            .buscar(restauranteId)).withRel(rel);
	}

	public static Link linkToRestaurante(Long restauranteId) {
	    return linkToRestaurante(restauranteId, IanaLinkRelations.SELF.value());
	}

	public static Link linkToUsuario(Long usuarioId, String rel) {
	    return linkTo(methodOn(UsuarioController.class)
	            .buscar(usuarioId)).withRel(rel);
	}

	public static Link linkToUsuario(Long usuarioId) {
	    return linkToUsuario(usuarioId, IanaLinkRelations.SELF.value());
	}

	public static Link linkToUsuarios(String rel) {
	    return linkTo(UsuarioController.class).withRel(rel);
	}

	public static Link linkToUsuarios() {
	    return linkToUsuarios(IanaLinkRelations.SELF.value());
	}

	public static Link linkToGruposUsuario(Long usuarioId, String rel) {
	    return linkTo(methodOn(UsuarioGrupoController.class)
	            .listar(usuarioId)).withRel(rel);
	}

	public static Link linkToGruposUsuario(Long usuarioId) {
	    return linkToGruposUsuario(usuarioId, IanaLinkRelations.SELF.value());
	}

	public static Link linkToResponsaveisRestaurante(Long restauranteId, String rel) {
	    return linkTo(methodOn(RestauranteUsuarioResponsavelController.class)
	            .listar(restauranteId)).withRel(rel);
	}

	public static Link linkToResponsaveisRestaurante(Long restauranteId) {
	    return linkToResponsaveisRestaurante(restauranteId, IanaLinkRelations.SELF.value());
	}

	public static Link linkToFormaPagamento(Long formaPagamentoId, String rel) {
	    return linkTo(methodOn(FormaPagamentoController.class)
	            .buscar(formaPagamentoId, null)).withRel(rel);
	}

	public static Link linkToFormaPagamento(Long formaPagamentoId) {
	    return linkToFormaPagamento(formaPagamentoId, IanaLinkRelations.SELF.value());
	}

	public static Link linkToCidade(Long cidadeId, String rel) {
	    return linkTo(methodOn(CidadeController.class)
	            .buscar(cidadeId)).withRel(rel);
	}

	public static Link linkToCidade(Long cidadeId) {
	    return linkToCidade(cidadeId, IanaLinkRelations.SELF.value());
	}

	public static Link linkToCidades(String rel) {
	    return linkTo(CidadeController.class).withRel(rel);
	}

	public static Link linkToCidades() {
	    return linkToCidades(IanaLinkRelations.SELF.value());
	}

	public static Link linkToEstado(Long estadoId, String rel) {
	    return linkTo(methodOn(EstadoController.class)
	            .buscar(estadoId)).withRel(rel);
	}

	public static Link linkToEstado(Long estadoId) {
	    return linkToEstado(estadoId, IanaLinkRelations.SELF.value());
	}

	public static Link linkToEstados(String rel) {
	    return linkTo(EstadoController.class).withRel(rel);
	}

	public static Link linkToEstados() {
	    return linkToEstados(IanaLinkRelations.SELF.value());
	}

	public static Link linkToProduto(Long restauranteId, Long produtoId, String rel) {
	    return linkTo(methodOn(RestauranteProdutoController.class)
	            .buscar(restauranteId, produtoId))
	            .withRel(rel);
	}

	public static Link linkToProduto(Long restauranteId, Long produtoId) {
	    return linkToProduto(restauranteId, produtoId, IanaLinkRelations.SELF.value());
	}

	public static Link linkToCozinhas(String rel) {
	    return linkTo(CozinhaController.class).withRel(rel);
	}

	public static Link linkToCozinhas() {
	    return linkToCozinhas(IanaLinkRelations.SELF.value());
	}

}
