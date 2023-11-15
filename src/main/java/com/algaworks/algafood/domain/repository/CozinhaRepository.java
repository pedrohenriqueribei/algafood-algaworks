package com.algaworks.algafood.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algaworks.algafood.domain.model.Gastronomia;

//@Repository
public interface CozinhaRepository extends JpaRepository<Gastronomia, Long>{
	List<Gastronomia> findByNomeContaining(String nome);
	List<Gastronomia> findByNome(String nome);
	
	boolean existsByNome(String nome);
}
