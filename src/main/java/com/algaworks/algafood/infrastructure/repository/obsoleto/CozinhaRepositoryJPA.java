package com.algaworks.algafood.infrastructure.repository.obsoleto;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

@Repository
public class CozinhaRepositoryJPA implements CozinhaRepository {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Cozinha> todas() {
		return manager.createQuery("from Cozinha", Cozinha.class).getResultList();
	}
	
	@Override
	public List<Cozinha> consultarPorNome(String nome) {
		// TODO realizar implementação da funcionalidade de buscar por nome de cozinha
		return manager.createQuery("FROM Cozinha WHERE nome LIKE :nome", Cozinha.class)
				.setParameter("nome", "%"+ nome +"%")
				.getResultList();
	}

	@Override
	public Cozinha buscar(Long id) {
		return manager.find(Cozinha.class, id);
	}

	@Override
	@Transactional
	public Cozinha salvar(Cozinha cozinha) {
		return manager.merge(cozinha);
	}

	@Override
	@Transactional
	public void remover(Long id) {
		Cozinha cozinha = buscar(id); 
		
		if(cozinha == null) {
			throw new EmptyResultDataAccessException(1);
		}
		manager.remove(cozinha);
	}

}
