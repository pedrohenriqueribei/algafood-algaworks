package com.algaworks.algafood.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.ProdutoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {


	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;
	
	@Autowired
	private CadastroCidadeService cadastroCidadeService;
	
	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamentoService; 
	
	@Autowired
	private CadastroUsuarioService cadastroUsuarioService;
	
	@Transactional
	public Restaurante salvar (Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		Long cidadeId = restaurante.getEndereco().getCidade().getId();
		
		Cozinha cozinha = cadastroCozinhaService.buscarOuFalhar(cozinhaId);
		Cidade cidade = cadastroCidadeService.buscarOufahar(cidadeId);
		
		restaurante.setCozinha(cozinha);
		restaurante.getEndereco().setCidade(cidade);
		
		return restauranteRepository.save(restaurante);
	}	
	
	@Transactional
	public void ativar(Long restauranteId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		
		restaurante.ativar();
	}
	
	@Transactional
	public void inativar(Long restauranteId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		
		restaurante.inativar();
	}
	
	public Restaurante buscarOuFalhar (Long restauranteId) {
		return restauranteRepository.findById(restauranteId).orElseThrow(() -> new RestauranteNaoEncontradoException(restauranteId));
	}
	
	@Transactional
	public void adicionarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscarOuFalhar(formaPagamentoId);
		
		restaurante.adicionarFormaPagamento(formaPagamento);
	}
	
	@Transactional
	public void removerFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscarOuFalhar(formaPagamentoId);
		
		restaurante.removerFormaPagamento(formaPagamento);
	}
	
	public Produto buscarProduto(Long restauranteId, Long produtoId) {
		Optional<Produto> produto = produtoRepository.findProdutoById(produtoId);
		
		if(!produto.isPresent() || produto.get().getRestaurante().getId() != restauranteId) {
			throw new ProdutoNaoEncontradoException(restauranteId, produtoId);
		} 
		
		return produto.get();
	}
	
	@Transactional
	public void abrirRestaurante(Long restauranteId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		restaurante.abrir();
	}
	
	@Transactional
	public void fecharRestaurante(Long restauranteId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		restaurante.fechar();
	}

	@Transactional
	public void associarResponsavel(Long restauranteId, Long responsavelId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		Usuario usuario = cadastroUsuarioService.buscarOuFalhar(responsavelId);
		
		restaurante.adicionarResponsavel(usuario);
	}

	@Transactional
	public void desassociarResponsavel(Long restauranteId, Long responsavelId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		Usuario usuario = cadastroUsuarioService.buscarOuFalhar(responsavelId);
		
		restaurante.removerResponsavel(usuario);
	}
}
