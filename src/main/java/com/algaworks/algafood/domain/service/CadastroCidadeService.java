package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;

@Service
public class CadastroCidadeService {

	@Autowired
	private CidadeRepository cidadeRepository;
	
	public Cidade salvar(Cidade cidade) {
		return cidadeRepository.save(cidade);
	}
	
	public void excluir(Long id) {
		System.out.println("Chegou no método excluir cidade");
		try {
			cidadeRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException("Não existe cidade com este código: "+id);
		} catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException("Cidade não pode ser removida!!");
		}
	}
	
}
