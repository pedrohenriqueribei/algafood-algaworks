package com.algaworks.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;


@Component
public class FormaPagamentoRepositoryJPA implements FormaPagamentoRepository{

	
	@PersistenceContext
	private EntityManager manager;
	
	
	@Override
	public List<FormaPagamento> todas() {
		// TODO Auto-generated method stub
		return manager.createQuery("from FormaPagamento", FormaPagamento.class).getResultList();
	}

	@Override
	public FormaPagamento buscar(Long id) {
		// TODO Auto-generated method stub
		return manager.find(FormaPagamento.class, id);
	}

	@Override
	@Transactional
	public FormaPagamento salvar(FormaPagamento formaPagamento) {
		// TODO Auto-generated method stub
		return manager.merge(formaPagamento);
	}

	@Override
	@Transactional
	public void remover(FormaPagamento formaPagamento) {
		// TODO Auto-generated method stub
		formaPagamento = buscar(formaPagamento.getId());
		manager.remove(formaPagamento);
	}

}
