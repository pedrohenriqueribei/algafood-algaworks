package com.algaworks.algafood.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {
	
	private static final long serialVersionUID = 1L;

	public PedidoNaoEncontradoException (String mensagem) {
		super(mensagem);
	}
	
	public PedidoNaoEncontradoException (Long id) {
		this("Não existe um cadastro de um pedido com o código: "+ id);
	}

}
