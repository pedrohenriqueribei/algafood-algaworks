package com.algaworks.algafood.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.EstadoDTOAssembler;
import com.algaworks.algafood.api.disassembler.EstadoDisassembler;
import com.algaworks.algafood.api.model.DTO.input.EstadoDTOinput;
import com.algaworks.algafood.api.model.DTO.output.EstadoDTO;
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
	
	@Autowired
	private EstadoDTOAssembler estadoAssembler;
	
	@Autowired
	private EstadoDisassembler estadoDisassembler;
	
	@GetMapping
	public List<EstadoDTO> listar(){
		return estadoAssembler.toCollectDTO(estadoRepository.findAll());
	}
	
	@GetMapping("{id}")
	public EstadoDTO buscar (@PathVariable Long id){
		return estadoAssembler.toDTO(cadastroEstadoService.buscarOuFalhar(id));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstadoDTO adicionar (
			@RequestBody 
			@Valid 
			EstadoDTOinput estadoDTOinput) {
		
		Estado estado = estadoDisassembler.paraDominio(estadoDTOinput);
		
		return  estadoAssembler.toDTO(cadastroEstadoService.salvar(estado));
	}
	
	@PutMapping("{id}")
	public EstadoDTO atualizar(@PathVariable Long id, @RequestBody @Valid EstadoDTOinput estadoDTOinput) {
		Estado estado = cadastroEstadoService.buscarOuFalhar(id);
		
		estadoDisassembler.copiarParaDominio(estadoDTOinput, estado);
		
		return estadoAssembler.toDTO(cadastroEstadoService.salvar(estado));
	}
	
	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar (@PathVariable Long id) {
		cadastroEstadoService.excluir(id);
	}
}

