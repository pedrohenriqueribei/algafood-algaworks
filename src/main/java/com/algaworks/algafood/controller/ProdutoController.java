package com.algaworks.algafood.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.ProdutoDTOAssembler;
import com.algaworks.algafood.api.disassembler.ProdutoDisassembler;
import com.algaworks.algafood.api.model.DTO.input.ProdutoComRestauranteDTOinput;
import com.algaworks.algafood.api.model.DTO.output.ProdutoDTO;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.service.CadastroProdutoService;

@RestController
@RequestMapping("produtos")
public class ProdutoController {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CadastroProdutoService cadastroProdutoService;
	
	@Autowired
	private ProdutoDTOAssembler produtoDTOAssembler;
	
	@Autowired
	private ProdutoDisassembler produtoDisassembler;
	
	@GetMapping
	public List<ProdutoDTO> listar(){
		return produtoDTOAssembler.paraColecaoDTO(produtoRepository.findAll());
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public ProdutoDTO incluir (@RequestBody ProdutoComRestauranteDTOinput produtoDTOinput) {
		try {
			Produto produto = produtoDisassembler.toDomainObject(produtoDTOinput);
			Produto produtoPersist = cadastroProdutoService.salvar(produto);
			return produtoDTOAssembler.paraDTO(produtoPersist);
		} catch(EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}
}
