package com.algaworks.algafood.domain.exception;

//@ResponseStatus(value = HttpStatus.NOT_FOUND) //, reason = "Entidade não encontrada")
public class EstadoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;
	
	
	public EstadoNaoEncontradoException (String mensagem) {
		super(mensagem);
	}
	
	public EstadoNaoEncontradoException (Long estadoId) {
		this("Não existe um cadastro de estado com o código: "+ estadoId);
	}
}
