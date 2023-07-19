package com.algaworks.algafood.domain.repository;


import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algaworks.algafood.domain.model.Restaurante;

//@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long>{
/*
	List<Restaurante> listar();
	Restaurante buscar(Long id);
	Restaurante salvar(Restaurante restaurante);
	void remover (Restaurante restaurante);
*/
	
	List<Restaurante> findByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);
	List<Restaurante> findByNomeContainingAndCozinhaId(String nome, Long cozinhaId);
}

