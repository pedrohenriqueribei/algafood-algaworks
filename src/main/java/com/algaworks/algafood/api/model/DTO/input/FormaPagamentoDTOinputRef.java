package com.algaworks.algafood.api.model.DTO.input;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FormaPagamentoDTOinputRef {
	
	@NotNull
	private Long id;
}
