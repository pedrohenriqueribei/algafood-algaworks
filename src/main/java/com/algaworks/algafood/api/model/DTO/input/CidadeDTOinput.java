package com.algaworks.algafood.api.model.DTO.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CidadeDTOinput {
	
	@NotBlank
	private String nome;
	
	@Valid
	@NotNull
	private EstadoDtoInputRef estado;

}
