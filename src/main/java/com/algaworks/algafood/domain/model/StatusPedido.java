package com.algaworks.algafood.domain.model;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;

public @Getter enum StatusPedido {

	CRIADO("Criado"),
	CONFIRMADO("Confirmado", CRIADO),
	ENTREGUE("Entregue", CONFIRMADO),
	CANCELADO("Cancelado", CRIADO);
	
	private String descricao;
	private List<StatusPedido> statusAnterioes;
	
	StatusPedido(String descricao, StatusPedido... statusAnterioes) {
		this.descricao = descricao;
		this.statusAnterioes = Arrays.asList(statusAnterioes);
	}
	
	public boolean naoPodeAlterarPara(StatusPedido novoStatus) {
		return !novoStatus.getStatusAnterioes().contains(this);
	}
	
	
}