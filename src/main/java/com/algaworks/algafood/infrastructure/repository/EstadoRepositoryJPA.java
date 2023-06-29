package com.algaworks.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;


@Component
public class EstadoRepositoryJPA implements EstadoRepository {

	@PersistenceContext
	private EntityManager manager;
	
	
	@Override
	public List<Estado> listar() {
		// TODO Auto-generated method stub
		return manager.createQuery("from Estado", Estado.class).getResultList();
	}

	@Override
	public Estado buscar(Long id) {
		// TODO Auto-generated method stub
		return manager.find(Estado.class, id);
	}

	@Override
	@Transactional
	public Estado salvar(Estado estado) {
		// TODO Auto-generated method stub
		return manager.merge(estado);
	}

	@Override
	@Transactional
	public void remover(Long id) {
		// TODO Auto-generated method stub
		Estado estado = buscar(id);
		
		if (estado == null) {
            throw new EmptyResultDataAccessException(1);
        }
		
		manager.remove(estado);
	}

}
