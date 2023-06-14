package com.algaworks.algafood.jpa.restaurante;


import java.math.BigDecimal;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

public class InclusaoRestauranteMain {

	public static void main(String[] args) {

		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		RestauranteRepository restauranteRepository = applicationContext.getBean(RestauranteRepository.class);
		CozinhaRepository cozinhaRepository = applicationContext.getBean(CozinhaRepository.class);
		
		Restaurante rest1 = new Restaurante();
		rest1.setNome("Mc Donald's");
		rest1.setTaxaFrete(new BigDecimal(11.3));
		rest1.setCozinha(cozinhaRepository.buscar(1L));
		
		
		Restaurante rest2 = new Restaurante();
		rest2.setNome("Madero");
		rest2.setTaxaFrete(new BigDecimal(31.8));
		rest2.setCozinha(cozinhaRepository.buscar(1L));
		
		restauranteRepository.salvar(rest1);
		restauranteRepository.salvar(rest2);
	}

}
