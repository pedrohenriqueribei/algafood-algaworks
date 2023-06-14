package com.algaworks.algafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

public class TesteEstadoMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		
		EstadoRepository estadoRepository = applicationContext.getBean(EstadoRepository.class);
		
		List<Estado> lista = estadoRepository.listar();
		
		lista.forEach(uf -> System.out.println(uf.getNome()));
	}

}
