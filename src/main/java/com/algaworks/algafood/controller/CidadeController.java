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
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
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
		return ResponseEntity.ok(cidadeRepository.listar());
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Cidade> buscar (@PathVariable Long id) {
		Cidade cidade = cidadeRepository.buscar(id);
		
		if(cidade != null) {
			return ResponseEntity.ok(cidade);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Cidade> adicionar (@RequestBody Cidade cidade) {
		
		Cidade cidadeAtual = cadastroCidadeService.salvar(cidade);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(cidadeAtual);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<?> atualizar (@PathVariable Long id, @RequestBody Cidade cidade) {
		Cidade cidadeAtual = cidadeRepository.buscar(id);
		
		if(cidadeAtual != null) {
			BeanUtils.copyProperties(cidade, cidadeAtual, "id");
			cidadeAtual = cadastroCidadeService.salvar(cidadeAtual);
			
			return ResponseEntity.ok(cidadeAtual);
		}
		
		return ResponseEntity.badRequest().body("Cidade não encontrada!!");
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Cidade> deletar (@PathVariable Long id) {
		System.out.println("Chegou no método deletar cidade");
		
		try {
			cadastroCidadeService.excluir(id);
			return ResponseEntity.noContent().build();
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}

}
