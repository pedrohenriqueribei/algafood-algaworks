package com.algaworks.algafood.domain.exception;

//@ResponseStatus(value = HttpStatus.NOT_FOUND) //, reason = "Entidade não encontrada")
public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;
	
	
	public CidadeNaoEncontradaException (String mensagem) {
		super(mensagem);
	}
	
	public CidadeNaoEncontradaException (Long cidadeId) {
		this("Não existe Cidade com o código "+ cidadeId);
	}

}
