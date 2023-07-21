package com.algaworks.algafood.domain.repository;


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.algaworks.algafood.domain.model.Restaurante;

//@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long>{
/*
	List<Restaurante> listar();
	Restaurante buscar(Long id);
	Restaurante salvar(Restaurante restaurante);
	void remover (Restaurante restaurante);
	
	Outros prefixos:
	List<Restaurante> readByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);
	List<Restaurante> queryByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);
	List<Restaurante> getByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);
	List<Restaurante> streamByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);
*/
	
	//List<Restaurante> streamByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);
	@Query("from Restaurante WHERE taxaFrete >= :taxaInicial AND taxaFrete <= :taxaFinal")
	List<Restaurante> taxaFreteEntre(BigDecimal taxaInicial, BigDecimal taxaFinal);
	
//	List<Restaurante> findByNomeContainingAndCozinhaId (String nome, Long cozinhaId);
//	@Query("FROM Restaurante WHERE nome LIKE %:nome% AND cozinha.id = :id")
	List<Restaurante> consultarPorNome (@Param("nome") String pessoa, @Param("id") Long cozinhaId);
	
	Optional<Restaurante> findFirstRestauranteByNomeContaining(String nome);
	List<Restaurante> findTop2RestauranteByNomeContaining(String nome);
	
	boolean existsByNome(String nome);
	int countByCozinhaId (Long cozinha);
	
	List<Restaurante> find (String nome, BigDecimal taxaInicial, BigDecimal taxaFinal);
	
}

