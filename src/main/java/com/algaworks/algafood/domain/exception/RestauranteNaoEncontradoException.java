package com.algaworks.algafood.domain.exception;

//@ResponseStatus(value = HttpStatus.NOT_FOUND) //, reason = "Entidade não encontrada")
public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;
	
	
	public RestauranteNaoEncontradoException (String mensagem) {
		super(mensagem);
	}
	
	public RestauranteNaoEncontradoException (Long restauranteId) {
		this("Não existe um Restaurante com o código: "+ restauranteId);
	}
}
