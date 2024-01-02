package com.algaworks.algafood.api.model.DTO.input;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PermissaoDTOinput {

	@NotBlank
	private String nome;
	
	@NotBlank
	private String descricao;
}
