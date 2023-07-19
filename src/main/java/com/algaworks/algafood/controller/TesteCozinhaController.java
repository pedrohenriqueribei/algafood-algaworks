package com.algaworks.algafood.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@RestController
@RequestMapping("teste")
public class TesteCozinhaController {

	@Autowired
	private CozinhaRepository cozinhaRepository;
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@GetMapping("cozinhas/porNome")
	public List<Cozinha> cozinhasPorNome(@RequestParam("nome") String nome) {
		return cozinhaRepository.findByNomeContaining(nome); 
	}
	
	@GetMapping("cozinhas/porNomeUnica")
	public List<Cozinha> cozinhasPorNomeUnica(@RequestParam("nome") String nome) {
		return cozinhaRepository.findByNome(nome); 
	}
	
	@GetMapping("restaurantes/porTaxaFrete")
	public List<Restaurante> restaurantePorTaxaFrete(BigDecimal taxaInicial, BigDecimal taxaFinal){
		return restauranteRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal);
	}
	
	@GetMapping("restaurantes/porNomeAndCozinhaId")
	public List<Restaurante> restaurantesPorNomeAndCozinhaId(String nome, Long id){
		return restauranteRepository.findByNomeContainingAndCozinhaId(nome, id);
	}
}
