package com.algaworks.algafood.infrastructure.repository.obsoleto;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.PermissaoRepository;


@Component
public class PermissaoRepositoryJPA implements PermissaoRepository {

	
	@PersistenceContext
	private EntityManager manager;
	
	
	@Override
	public List<Permissao> listar() {
		// TODO Auto-generated method stub
		return manager.createQuery("from Permissao", Permissao.class).getResultList();
	}

	@Override
	public Permissao buscar(Long id) {
		// TODO Auto-generated method stub
		return manager.find(Permissao.class, id);
	}

	@Override
	@Transactional
	public Permissao salvar(Permissao permissao) {
		// TODO Auto-generated method stub
		return manager.merge(permissao);
	}

	@Override
	@Transactional
	public void remover(Permissao permissao) {
		// TODO Auto-generated method stub
		permissao = buscar(permissao.getId());
		manager.remove(permissao);
	}

}
