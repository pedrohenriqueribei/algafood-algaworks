package com.algaworks.algafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;

public class TesteFormaPagamentoMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		FormaPagamentoRepository formaPagamentoRepository = applicationContext.getBean(FormaPagamentoRepository.class);
		
		List<FormaPagamento> formas = formaPagamentoRepository.todas();
		
		for (FormaPagamento forma : formas) {
			System.out.println(forma.getDescricao());
		}
		
		System.out.println();
		System.out.println();
		
		FormaPagamento formaPagamento = formaPagamentoRepository.buscar(1l);
		System.out.println("Forma de pagamento buscado: "+ formaPagamento.getDescricao());
		
		formaPagamento.setDescricao("Cédula");
		formaPagamentoRepository.salvar(formaPagamento);
		
		System.out.println("Atualizado: "+ formaPagamento.getDescricao());
		
		System.out.println();
		System.out.println();
		
		FormaPagamento cheque = new FormaPagamento();
		cheque.setDescricao("Cheque");
		formaPagamentoRepository.salvar(cheque);
		
		List<FormaPagamento> formas2 = formaPagamentoRepository.todas();
		for (FormaPagamento forma : formas2) {
			System.out.println(forma.getDescricao());
		}
	}

}
