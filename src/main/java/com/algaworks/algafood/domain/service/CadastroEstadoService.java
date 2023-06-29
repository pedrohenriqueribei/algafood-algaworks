package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {

	@Autowired
	private EstadoRepository estadoRepository;
	
	public Estado salvar (Estado estado) {
		return estadoRepository.salvar(estado);
	}
	
	public void excluir(Long id) {
		try {
			Estado estado = estadoRepository.buscar(id);
			estadoRepository.remover(estado);
		} catch (EmptyResultDataAccessException e) {
			// TODO: handle exception
			throw new EntidadeNaoEncontradaException("Não existe cadastro de Estado com código: "+ id);
		} catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException("Estado não pode ser removido!!");
		}
	}
}
