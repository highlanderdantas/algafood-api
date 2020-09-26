package com.algaworks.algafood.domain.event;

import com.algaworks.algafood.domain.model.Pedido;

import lombok.Getter;

@Getter
public class PedidoConfirmadoEvent extends PedidoEvent {

	public PedidoConfirmadoEvent(Pedido pedido) {
		super(pedido);
	}

}