package com.algaworks.algafood.domain.exception;

//@ResponseStatus(value = HttpStatus.NOT_FOUND) //, reason = "Entidade não encontrada")
public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;
	
	
	public CozinhaNaoEncontradaException (String mensagem) {
		super(mensagem);
	}
	
	public CozinhaNaoEncontradaException (Long cidadeId) {
		this("Não existe uma Cozinha com o código "+ cidadeId);
	}

}
