package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService {
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	
	public Cozinha salvar (Cozinha cozinha) {
		return cozinhaRepository.salvar(cozinha);
	}
	
	public void excluir (Long id) {
		try {
			cozinhaRepository.remover(id);			
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException("Não existe um cadastro de cozinha com código: "+ id);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException("Cozinha de código "+ id + " não pode ser removida. Está em uso!!!  ");
		}
	}

}
