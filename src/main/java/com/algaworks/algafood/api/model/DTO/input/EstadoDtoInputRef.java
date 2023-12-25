package com.algaworks.algafood.api.model.DTO.input;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoDtoInputRef {

	@NotNull
	private Long id;
}
