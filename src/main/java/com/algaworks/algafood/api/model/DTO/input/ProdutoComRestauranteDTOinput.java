package com.algaworks.algafood.api.model.DTO.input;

import javax.validation.Valid;

import com.algaworks.algafood.domain.model.Restaurante;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoComRestauranteDTOinput extends ProdutoDTOinput {

	@Valid
	private Restaurante restaurante;
}
