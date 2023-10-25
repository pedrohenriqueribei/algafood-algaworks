package com.algaworks.algafood.domain.exception;

//@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NegocioException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NegocioException(String message) {
		super(message);
	}

	public NegocioException(String message, Throwable causa) {
		super(message, causa);
	}

	
}
