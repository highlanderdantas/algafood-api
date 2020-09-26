package com.algaworks.algafood.domain.event;

import com.algaworks.algafood.domain.model.Pedido;

import lombok.Getter;

@Getter
public class PedidoCanceladoEvent extends PedidoEvent {

	public PedidoCanceladoEvent(Pedido pedido) {
		super(pedido);
	}
	
}