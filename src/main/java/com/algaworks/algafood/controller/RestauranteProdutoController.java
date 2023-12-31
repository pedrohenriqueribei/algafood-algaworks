package com.algaworks.algafood.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.ProdutoDTOAssembler;
import com.algaworks.algafood.api.disassembler.ProdutoDisassembler;
import com.algaworks.algafood.api.model.DTO.input.ProdutoDTOinput;
import com.algaworks.algafood.api.model.DTO.output.ProdutoDTO;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("restaurantes/{restauranteId}/produto")
public class RestauranteProdutoController {
	
	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;
	
	@Autowired
	private ProdutoDTOAssembler produtoDTOAssembler;
	
	@Autowired
	private ProdutoDisassembler produtoDisassembler;
	
	@Autowired
	private CadastroProdutoService cadastroProdutoService;
	
	@GetMapping
	public List<ProdutoDTO> listar (@PathVariable Long restauranteId){
		Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);
		
		return produtoDTOAssembler.paraColecaoDTO(restaurante.getProdutos());
	}
	
	@GetMapping("{produtoId}")
	public ProdutoDTO buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		return produtoDTOAssembler.paraDTO(cadastroRestauranteService.buscarProduto(restauranteId, produtoId));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProdutoDTO adicionarProduto(
			@PathVariable
			Long restauranteId, 
			@RequestBody
			@Valid
			ProdutoDTOinput produtoDTOinput) {
		
		try {
			Produto produto = produtoDisassembler.toDomainObject(produtoDTOinput);
			Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);
			produto.setRestaurante(restaurante);
			Produto produtoPersist = cadastroProdutoService.salvar(produto);
			return produtoDTOAssembler.paraDTO(produtoPersist);
		} catch(EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	@PutMapping("{produtoId}")
	public ProdutoDTO atualizarProduto(
			@PathVariable
			Long restauranteId, 
			@PathVariable
			Long produtoId, 
			@RequestBody
			@Valid
			ProdutoDTOinput produtoDTOinput) {
		
		try {
			Produto produto = cadastroProdutoService.buscarOuFalhar(produtoId);
			Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);
			produto.setRestaurante(restaurante);
			produtoDisassembler.copiarParaDominio(produtoDTOinput, produto);
			return produtoDTOAssembler.paraDTO(cadastroProdutoService.salvar(produto));
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
		
	}
}
