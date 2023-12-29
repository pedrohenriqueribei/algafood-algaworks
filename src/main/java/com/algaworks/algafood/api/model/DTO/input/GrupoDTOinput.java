package com.algaworks.algafood.api.model.DTO.input;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GrupoDTOinput {

	@NotBlank
	private String nome;
}
