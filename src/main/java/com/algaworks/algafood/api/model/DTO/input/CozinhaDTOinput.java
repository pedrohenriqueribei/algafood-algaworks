package com.algaworks.algafood.api.model.DTO.input;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CozinhaDTOinput {
	
	@NotBlank
	private String nome;
}
