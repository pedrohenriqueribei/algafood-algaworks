package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;

@Service
public class CadastroCidadeService {

	private static final String MSG_CIDADE_NÃO_PODE_SER_REMOVIDA = "Cidade não pode ser removida!!";
	private static final String MSG_NÃO_EXISTE_CIDADE_COM_ESTE_CÓDIGO = "Não existe cidade com este código: ";
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroEstadoService cadastroEstadoService;
	
	public Cidade salvar(Cidade cidade) {
		Long estadoId = cidade.getEstado().getId();
		
		Estado estado = cadastroEstadoService.buscarOuFalhar(estadoId);
		
		cidade.setEstado(estado);
		
		return cidadeRepository.save(cidade);
	}
	
	public void excluir(Long id) {
		System.out.println("Chegou no método excluir cidade");
		try {
			cidadeRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(MSG_NÃO_EXISTE_CIDADE_COM_ESTE_CÓDIGO+id);
		} catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(MSG_CIDADE_NÃO_PODE_SER_REMOVIDA);
		}
	}
	
	public Cidade buscarOufahar(Long cidadeId) {
		return cidadeRepository.findById(cidadeId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException(MSG_NÃO_EXISTE_CIDADE_COM_ESTE_CÓDIGO));
	}
	
}
