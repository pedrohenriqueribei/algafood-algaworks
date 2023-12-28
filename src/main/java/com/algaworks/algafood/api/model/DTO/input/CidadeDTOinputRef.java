package com.algaworks.algafood.api.model.DTO.input;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CidadeDTOinputRef {

	@NotNull
	private Long id;
}
