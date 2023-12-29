package com.algaworks.algafood.api.model.DTO.input;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UsuarioAlterarSenhaDTOinput {

	@NotBlank
	private String senhaAtual;
	
	@NotBlank
	private String novaSenha;
	
	
}
