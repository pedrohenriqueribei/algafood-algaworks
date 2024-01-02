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

import com.algaworks.algafood.api.assembler.PermissaoDTOAssembler;
import com.algaworks.algafood.api.disassembler.PermissaoDTODisassembler;
import com.algaworks.algafood.api.model.DTO.input.PermissaoDTOinput;
import com.algaworks.algafood.api.model.DTO.output.PermissaoDTO;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.service.CadastroPermissaoService;

@RestController
@RequestMapping("permissoes")
public class PermissaoController {

	@Autowired
	private CadastroPermissaoService cadastroPermissaoService;
	
	@Autowired
	private PermissaoDTOAssembler permissaoDTOAssembler;
	
	@Autowired
	private PermissaoDTODisassembler permissaoDTOdisassembler;
	
	@GetMapping
	public List<PermissaoDTO> listar() {
		return permissaoDTOAssembler.toCollectDTO(cadastroPermissaoService.todos());
	}
	
	@GetMapping("{permissaoId}")
	public PermissaoDTO buscar(@PathVariable Long permissaoId) {
		return permissaoDTOAssembler.toDTO(cadastroPermissaoService.buscarOuFalhar(permissaoId));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PermissaoDTO adicionar(
			@RequestBody
			@Valid
			PermissaoDTOinput permissaoDTOinput) {
		
		Permissao permissao = permissaoDTOdisassembler.toDomainObject(permissaoDTOinput);
		
		return permissaoDTOAssembler.toDTO(cadastroPermissaoService.salvar(permissao));
	}
	
	@PutMapping("{permissaoId}")
	public PermissaoDTO atualizar(
			@PathVariable Long permissaoId,
			@RequestBody
			@Valid
			PermissaoDTOinput permissaoDTOinput) {
		
		Permissao permissao = cadastroPermissaoService.buscarOuFalhar(permissaoId);
		permissaoDTOdisassembler.copiarParaDominio(permissaoDTOinput, permissao);
		
		return permissaoDTOAssembler.toDTO(permissao);
	}
	
	@DeleteMapping("{permissaoId}")
	public void deletar(@PathVariable Long permissaoId) {
		cadastroPermissaoService.excluir(permissaoId);
	}
}
