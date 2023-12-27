package com.algaworks.algafood.domain.exception;


public class FormaPagamentoNaoEncontradoException extends EntidadeNaoEncontradaException {

	public FormaPagamentoNaoEncontradoException(String mensagem) {
		super(mensagem);
		// TODO Auto-generated constructor stub
	}
	
	public FormaPagamentoNaoEncontradoException(Long id) {
		this("Não existe uma Forma de Pagamento com o código: "+ id);
	}

	private static final long serialVersionUID = 1L;

	
}
