package com.algaworks.algafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND) //, reason = "Entidade não encontrada")
public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;
	
	
	public CozinhaNaoEncontradaException (String mensagem) {
		super(mensagem);
	}
	
	public CozinhaNaoEncontradaException (Long cidadeId) {
		this("Não existe Cidade com o código "+ cidadeId);
	}

}
