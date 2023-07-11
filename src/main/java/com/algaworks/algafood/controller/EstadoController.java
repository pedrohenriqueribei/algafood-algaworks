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
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;

@RestController
@RequestMapping("estados")
public class EstadoController {

	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CadastroEstadoService cadastroEstadoService;
	
	@GetMapping
	public ResponseEntity<List<Estado>> listar(){
		return  ResponseEntity.accepted().body(estadoRepository.findAll());
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Estado> buscar (@PathVariable Long id){
		Estado estado = estadoRepository.findById(id).get();
		
		if(estado != null) {
			return ResponseEntity.ok(estado);			
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Estado> adicionar (@RequestBody Estado estado) {
		Estado estadoSalvar = cadastroEstadoService.salvar(estado);
		return ResponseEntity.status(HttpStatus.CREATED).body(estadoSalvar);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Estado estado) {
		Estado estadoAtual = estadoRepository.findById(id).get();
		
		if(estadoAtual != null) {
			BeanUtils.copyProperties(estado, estadoAtual, "id");
			estadoAtual = cadastroEstadoService.salvar(estadoAtual);
			
			return ResponseEntity.ok(estadoAtual);
		}
		
		return ResponseEntity.badRequest().body("Unidade da Federação não encontrada!!");
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> deletar (@PathVariable Long id) {
		try {
			cadastroEstadoService.excluir(id);
			return ResponseEntity.noContent().build();
		} catch(EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}
}

