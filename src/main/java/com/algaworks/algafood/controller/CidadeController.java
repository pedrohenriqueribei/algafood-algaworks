package com.algaworks.algafood.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;

@RestController
@RequestMapping("cidades")
public class CidadeController {

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroCidadeService cadastroCidadeService;
	
	@GetMapping
	public ResponseEntity<List<Cidade>> listar (){
		return ResponseEntity.ok(cidadeRepository.findAll());
	}
	
	@GetMapping("{id}")
	public Cidade buscar (@PathVariable Long id) {
		return cadastroCidadeService.buscarOufahar(id);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cidade adicionar (@RequestBody Cidade cidade) {
		return cadastroCidadeService.salvar(cidade);
	}
	
	@PutMapping("{id}")
	public Cidade atualizar (@PathVariable Long id, @RequestBody Cidade cidade) {
		
		Cidade cidadeAtual = cadastroCidadeService.buscarOufahar(id);
		
		BeanUtils.copyProperties(cidade, cidadeAtual, "id");
		
		return cadastroCidadeService.salvar(cidadeAtual);
	}
	
	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar (@PathVariable Long id) {
		System.out.println("Chegou no método deletar cidade");
		
		cadastroCidadeService.excluir(id);
	}

}
