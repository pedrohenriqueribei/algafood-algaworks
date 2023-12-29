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

import com.algaworks.algafood.api.assembler.UsuarioAssembler;
import com.algaworks.algafood.api.disassembler.UsuarioDisassembler;
import com.algaworks.algafood.api.model.DTO.input.UsuarioDTOinput;
import com.algaworks.algafood.api.model.DTO.output.UsuarioDTO;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {

	@Autowired
	private CadastroUsuarioService cadastroUsuarioService;
	
	@Autowired
	private UsuarioAssembler usuarioAssembler;
	
	@Autowired
	private UsuarioDisassembler usuarioDisassembler;
	
	@GetMapping
	public List<UsuarioDTO> listar(){
		return usuarioAssembler.toCollectDTO(cadastroUsuarioService.listar());
	}					
	
	@GetMapping("{id}")
	public UsuarioDTO buscar (@PathVariable Long id) {
		return usuarioAssembler.toDTO(cadastroUsuarioService.buscarOuFalhar(id));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioDTO adicionar (
			@RequestBody 
			@Valid 
			UsuarioDTOinput usuarioDTOinput) {
		
		Usuario usuario = usuarioDisassembler.toDomainObject(usuarioDTOinput);
		
		usuario = cadastroUsuarioService.salvar(usuario);
		
		UsuarioDTO usuarioDTO = usuarioAssembler.toDTO(usuario);
		
		return usuarioDTO;
	}
	
	@PutMapping("{id}")
	public UsuarioDTO atualizar (
			@PathVariable
			Long id, 
			@RequestBody
			@Valid
			UsuarioDTOinput usuarioDTOinput) {
		
		Usuario usuario = cadastroUsuarioService.buscarOuFalhar(id);
		usuarioDisassembler.copiarParaDominio(usuarioDTOinput, usuario);
		
		return usuarioAssembler.toDTO(cadastroUsuarioService.salvar(usuario));
	}
	
	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long id) {
		cadastroUsuarioService.excluir(id);
	}
}
