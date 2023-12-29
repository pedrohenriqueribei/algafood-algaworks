package com.algaworks.algafood.api.model.DTO.input;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UsuarioAtualizarDTOinput {

	@NotBlank
	private String nome;
	
	@NotBlank
	@Email
	private String email;
}
