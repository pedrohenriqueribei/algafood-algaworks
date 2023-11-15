package com.algaworks.algafood.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.model.Gastronomia;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
//import com.algaworks.algafood.infrastructure.repository.spec.RestauranteSpecs;

@RestController
@RequestMapping("teste")
public class TesteCozinhaController {

	@Autowired
	private CozinhaRepository cozinhaRepository;
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@GetMapping("cozinhas/porNome")
	public List<Gastronomia> cozinhasPorNome(@RequestParam("nome") String nome) {
		return cozinhaRepository.findByNomeContaining(nome); 
	}
	
	@GetMapping("cozinhas/porNomeUnica")
	public List<Gastronomia> cozinhasPorNomeUnica(@RequestParam("nome") String nome) {
		return cozinhaRepository.findByNome(nome); 
	}
	
	@GetMapping("cozinhas/exists")
	public boolean cozinhaExists(String nome) {
		return cozinhaRepository.existsByNome(nome);
	}
	
	@GetMapping("restaurantes/porTaxaFrete")
	public List<Restaurante> restaurantePorTaxaFrete(BigDecimal taxaInicial, BigDecimal taxaFinal){
		//return restauranteRepository.streamByTaxaFreteBetween(taxaInicial, taxaFinal);
		return restauranteRepository.taxaFreteEntre(taxaInicial, taxaFinal);
	}
	
	@GetMapping("restaurantes/porNomeAndCozinhaId")
	public List<Restaurante> restaurantesPorNomeAndCozinhaId(String nome, Long id){
//		return restauranteRepository.findByNomeContainingAndCozinhaId(nome, id);
		return restauranteRepository.consultarPorNome(nome, id);
	}
	
	@GetMapping("restaurante/primeiroPorNome")
	public Optional<Restaurante> primeiroPorNome(String nome) {
		return restauranteRepository.findFirstRestauranteByNomeContaining(nome);
	}
	
	@GetMapping("restaurantes/top2PorNome")
	public List<Restaurante> top2PorNome(String nome) {
		return restauranteRepository.findTop2RestauranteByNomeContaining(nome);
	}
	
	@GetMapping("restaurantes/countPorNome")
	public int contarPorNome(Long cozinhaId) {
		return restauranteRepository.countByCozinhaId(cozinhaId);
	}
	
	@GetMapping("/restaurantes/porNomeFrete")
	public List<Restaurante> restaurantesPorNomeFrete(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {
		return restauranteRepository.find(nome, taxaInicial, taxaFinal);
	}
	
	/**
	 * Specification DDD
	 */
	@GetMapping("/restaurantes/com-frete-gratis")
	public List<Restaurante> restaurantesComFreteGrati(String nome) {
		//var comFreteGratis = new RestauranteComFretegratisSpec();
		//var comNomeEquals =  new RestauranteComNomeSpec(nome);
		
		return restauranteRepository.findComFretegratis(nome);
	}
	
}
