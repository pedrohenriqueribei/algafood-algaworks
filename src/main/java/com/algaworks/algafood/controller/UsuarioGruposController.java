package com.algaworks.algafood.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.GrupoDTOAssembler;
import com.algaworks.algafood.api.model.DTO.output.GrupoDTO;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;

@RestController
@RequestMapping("usuarios/{usuarioId}/grupos")
public class UsuarioGruposController {
	
	@Autowired
	private CadastroUsuarioService cadastroUsuarioService;
	
	@Autowired
	private GrupoDTOAssembler grupoDTOAssembler;
	
	@GetMapping
	public List<GrupoDTO> listar (@PathVariable Long usuarioId){
		Usuario usuario = cadastroUsuarioService.buscarOuFalhar(usuarioId);
		
		return grupoDTOAssembler.toCollectDTO(usuario.getGrupos());
	}
	
	@PutMapping("{grupoId}")
	public void adicionarGrupoAoUsuario(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
		cadastroUsuarioService.incluirGrupo(usuarioId, grupoId);
	}
	
	@DeleteMapping("{grupoId}")
	public void removerGrupoAoUsuario(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
		cadastroUsuarioService.removerGrupo(usuarioId, grupoId);
	}

}
