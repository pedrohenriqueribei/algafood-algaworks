package com.algaworks.algafood.controller;

import java.util.List;

import javax.validation.Valid;

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
	public Estado buscar (@PathVariable Long id){
		return cadastroEstadoService.buscarOuFalhar(id);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Estado adicionar (@RequestBody @Valid Estado estado) {
		return cadastroEstadoService.salvar(estado);
	}
	
	@PutMapping("{id}")
	public Estado atualizar(@PathVariable Long id, @RequestBody @Valid Estado estado) {
		Estado estadoAtual = cadastroEstadoService.buscarOuFalhar(id);
		
		BeanUtils.copyProperties(estado, estadoAtual, "id");
		
		return cadastroEstadoService.salvar(estadoAtual);
	}
	
	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar (@PathVariable Long id) {
		cadastroEstadoService.excluir(id);
	}
}

