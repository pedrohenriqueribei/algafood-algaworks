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

import com.algaworks.algafood.api.assembler.GrupoDTOAssembler;
import com.algaworks.algafood.api.assembler.PermissaoDTOAssembler;
import com.algaworks.algafood.api.disassembler.GrupoDisassembler;
import com.algaworks.algafood.api.model.DTO.input.GrupoDTOinput;
import com.algaworks.algafood.api.model.DTO.output.GrupoDTO;
import com.algaworks.algafood.api.model.DTO.output.PermissaoDTO;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.service.CadastroGrupoService;

@RestController
@RequestMapping("grupos")
public class GrupoController {
	
	@Autowired
	private CadastroGrupoService cadastroGrupoService;
	
	@Autowired
	private GrupoDTOAssembler grupoDTOAssembler;
	
	@Autowired
	private GrupoDisassembler grupoDisassembler;
	
	@Autowired
	private PermissaoDTOAssembler permissaoDTOAssembler;
	
	@GetMapping
	public List<GrupoDTO> listar (){
		return grupoDTOAssembler.toCollectDTO(cadastroGrupoService.todos());
	}
	
	
	@GetMapping("{id}")
	public GrupoDTO buscar(@PathVariable Long id) {
		return grupoDTOAssembler.toDTO(cadastroGrupoService.buscarOuFalhar(id));
	}
	
	@GetMapping("{grupoId}/permissoes")
	public List<PermissaoDTO> listarPermissoes(@PathVariable Long grupoId) {
		Grupo grupo = cadastroGrupoService.buscarOuFalhar(grupoId);
		return permissaoDTOAssembler.toCollectDTO(grupo.getPermissoes());
	}
	
	@PutMapping("{grupoId}/permissoes/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associarPermissao(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		cadastroGrupoService.associarPermissao(grupoId, permissaoId);
	}
	
	@DeleteMapping("{grupoId}/permissoes/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desassociarPermissao(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		cadastroGrupoService.desassociarPermissao(grupoId, permissaoId);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public GrupoDTO adicionar (
			@RequestBody
			@Valid
			GrupoDTOinput grupoDTOinput) {
		
		
		Grupo grupo = grupoDisassembler.toDomain(grupoDTOinput);
		
		return grupoDTOAssembler.toDTO(cadastroGrupoService.salvar(grupo));
	
	}
	
	@PutMapping("{id}")
	public GrupoDTO atualizar (@PathVariable 
								Long id,
								@RequestBody
								@Valid
								GrupoDTOinput grupoDTOinput) {
		
		Grupo grupo = cadastroGrupoService.buscarOuFalhar(id);
		grupoDisassembler.copiarParaDominio(grupoDTOinput, grupo);
		
		return grupoDTOAssembler.toDTO(cadastroGrupoService.salvar(grupo));
	}
	
	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long id) {
		cadastroGrupoService.excluir(id);
	}
}
