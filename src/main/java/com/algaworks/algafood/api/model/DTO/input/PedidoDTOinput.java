package com.algaworks.algafood.api.model.DTO.input;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoDTOinput {
	
	@Valid
	@NotNull
	private RestauranteDTOinputRef restaurante;
	
	@Valid
	@NotNull
	private FormaPagamentoDTOinputRef formaPagamento;
	
	@Valid
	@NotNull
	private EnderecoDTOinput enderecoEntrega;
	
	@Valid
	@NotNull
	@Size(min = 1)
	private List<ItemPedidoDTOinput> itens;

}
