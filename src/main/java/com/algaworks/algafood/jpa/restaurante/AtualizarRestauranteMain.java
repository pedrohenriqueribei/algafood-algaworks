package com.algaworks.algafood.jpa.restaurante;


import java.math.BigDecimal;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

public class AtualizarRestauranteMain {

	public static void main(String[] args) {

		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		RestauranteRepository restauranteRepository = applicationContext.getBean(RestauranteRepository.class);
		CozinhaRepository cozinhaRepository = applicationContext.getBean(CozinhaRepository.class);
		
		Restaurante restaurante = new Restaurante();
		restaurante.setId(1L);
		restaurante.setNome("TaVaReS");
		restaurante.setTaxaFrete(new BigDecimal(50.1));
		restaurante.setCozinha(cozinhaRepository.buscar(1L));
		
		
		Restaurante rtrnt = restauranteRepository.adicionar(restaurante);
		System.out.println(rtrnt);
	}

}
