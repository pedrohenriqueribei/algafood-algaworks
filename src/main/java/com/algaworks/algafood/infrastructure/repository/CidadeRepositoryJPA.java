package com.algaworks.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;

@Component
public class CidadeRepositoryJPA implements CidadeRepository {

	
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Cidade> listar() {
		// TODO Auto-generated method stub
		return manager.createQuery("from Cidade", Cidade.class).getResultList();
	}

	@Override
	public Cidade buscar(Long id) {
		// TODO Auto-generated method stub
		return manager.find(Cidade.class, id);
	}

	@Override
	@Transactional
	public Cidade salvar(Cidade cidade) {
		// TODO Auto-generated method stub
		return manager.merge(cidade);
	}

	@Override
	@Transactional
	public void remover(Cidade cidade) {
		// TODO Auto-generated method stub
		cidade = buscar(cidade.getId());
		manager.remove(cidade);
	}

}
