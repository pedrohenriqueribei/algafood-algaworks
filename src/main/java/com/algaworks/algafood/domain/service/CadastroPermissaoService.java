package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.PermissaoNaoEncontradaException;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.PermissaoRepository;

@Service
public class CadastroPermissaoService {

	private static final String MGS_GRUPO_NEGADO = "Permissão Não pode ser excluida porque está em uso!!";
	
	@Autowired
	private PermissaoRepository permissaoRepository;
	
	public Permissao buscarOuFalhar(Long permissaoId) {
		return permissaoRepository.findById(permissaoId).orElseThrow(() -> new PermissaoNaoEncontradaException(permissaoId));
	}
	
	public List<Permissao> todos(){
		return permissaoRepository.findAll();
	}

	@Transactional
	public Permissao salvar(Permissao permissao) {
		return permissaoRepository.save(permissao);
	}
	
	@Transactional
	public void excluir(Long permissaoId) {
		try {
			permissaoRepository.deleteById(permissaoId);
			permissaoRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new PermissaoNaoEncontradaException(permissaoId);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(MGS_GRUPO_NEGADO + " Código: "+permissaoId);
		}
	}
}
