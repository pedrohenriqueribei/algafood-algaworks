package com.algaworks.algafood.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.UsuarioAssembler;
import com.algaworks.algafood.api.model.DTO.output.UsuarioDTO;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("restaurantes/{restauranteId}/responsaveis")
public class RestauranteUsuarioResponsavelController {
	
	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;
	
	@Autowired
	private UsuarioAssembler usuarioAssembler;

	@GetMapping
	public List<UsuarioDTO> listarResponsaveis(@PathVariable Long restauranteId){
		Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);
		return usuarioAssembler.toCollectDTO(restaurante.getUsuarios());
	}
	
	@PutMapping("{responsavelId}")
	public void associarResponsavel(@PathVariable Long restauranteId, @PathVariable Long responsavelId) {
		cadastroRestauranteService.associarResponsavel(restauranteId, responsavelId);
	}
	
	@DeleteMapping("{responsavelId}")
	public void desassociarResponsavel(@PathVariable Long restauranteId, @PathVariable Long responsavelId) {
		cadastroRestauranteService.desassociarResponsavel(restauranteId, responsavelId);
	}
}
