package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {

	private static final String MSG_ESTADO_NÃO_PODE_SER_REMOVIDO = "Estado não pode ser removido!!";

	@Autowired
	private EstadoRepository estadoRepository;
	
	public Estado salvar (Estado estado) {
		return estadoRepository.save(estado);
	}
	
	public void excluir(Long id) {
		try {
			
			estadoRepository.deleteById(id);
		} catch (EmptyResultDataAccessException | IllegalArgumentException e) {
			throw new EstadoNaoEncontradoException(id);
		} catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(MSG_ESTADO_NÃO_PODE_SER_REMOVIDO);
		}
		
	}
	
	public Estado buscarOuFalhar(Long estadoId) {
		return estadoRepository.findById(estadoId)
				.orElseThrow(() -> new EstadoNaoEncontradoException(estadoId));
	}
	
}
