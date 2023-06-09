package com.algaworks.algafood.jpa.cozinha;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Cozinha;

/**
 * Não esta sendo usado, foi atualizado para Cozinha Repository
 * @author Pedro
 *
 */
public class CadastroCozinha {

	@PersistenceContext
	private EntityManager manager;
	
	public List<Cozinha> listar(){
		  return manager.createQuery("from Cozinha", Cozinha.class).getResultList();
		
	}
	
	@Transactional
	public Cozinha salvar(Cozinha cozinha) {
		return manager.merge(cozinha);
	}
	
	public Cozinha buscar(Long id) {
		return manager.find(Cozinha.class, id);
	}
	
	@Transactional
	public void remover(Cozinha cozinha) {
		cozinha = buscar(cozinha.getId()); 
		manager.remove(cozinha);
	}
}
