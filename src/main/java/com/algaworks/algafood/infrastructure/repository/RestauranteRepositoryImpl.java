package com.algaworks.algafood.infrastructure.repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.algaworks.algafood.domain.model.Restaurante;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

	@PersistenceContext
	private EntityManager manager;
	
	
	/**
	 * método com parametros obrigatórios
	 */
//	@Override
//	public List<Restaurante> find (String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {
//		
//		var jpql = "from Restaurante where nome like :nome and taxaFrete between :taxaInicial and :taxaFinal";
//		
//		return manager.createQuery(jpql, Restaurante.class)
//				.setParameter("nome", "%"+nome+"%")
//				.setParameter("taxaInicial", taxaInicial)
//				.setParameter("taxaFinal", taxaFinal)
//				.getResultList();
//	}
	
	/**
	 * método com parâmetros opcionais com JPQL
	 */
	public List<Restaurante> find (String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {
		
		var jpql = new StringBuilder();
		jpql.append("from Restaurante where 0=0");
		
		var parametros = new HashMap<String, Object>();
		
		//verificar se o nome é nulo ou vazio
		if(StringUtils.hasLength(nome)) {
			jpql.append("and nome like :nome ");
			parametros.put("nome", "%" + nome + "%");
		}
		
		//verificar se taxa é nulo
		if(taxaInicial != null) {
			jpql.append("and taxaFrete >= :taxaInicial ");
			parametros.put("taxaInicial", taxaInicial);
		}
		
		//verificar se taxa é nulo
		if (taxaFinal != null) {
			jpql.append("and taxaFrete <= :taxaFinal ");
			parametros.put("taxaFinal", taxaFinal);
		}
		
		TypedQuery<Restaurante> query = manager.createQuery(jpql.toString(), Restaurante.class);
		
		parametros.forEach((chave, valor) -> query.setParameter(chave, valor));
		
		return query.getResultList();
				
	}
}
