package com.algaworks.algafood.jpa.cozinha;


import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

public class InclusaoCozinhaMain {

	public static void main(String[] args) {

		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		CozinhaRepository cozinhaRepository = applicationContext.getBean(CozinhaRepository.class);
		
		Cozinha coz1 = new Cozinha();
		coz1.setNome("Japonesa");
		
		Cozinha coz2 = new Cozinha();
		coz2.setNome("Italiana");
		
		cozinhaRepository.salvar(coz1);
		cozinhaRepository.salvar(coz2);
	}

}
