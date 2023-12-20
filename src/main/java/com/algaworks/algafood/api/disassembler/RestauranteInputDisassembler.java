package com.algaworks.algafood.api.disassembler;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.DTO.input.CozinhaDTOinputRef;
import com.algaworks.algafood.api.model.DTO.input.RestauranteDTOinput;
import com.algaworks.algafood.domain.model.Gastronomia;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteInputDisassembler {

	public Restaurante toDomainObject(RestauranteDTOinput restauranteDTOinput) {
		
		Restaurante restaurante = new Restaurante();
		restaurante.setNome(restauranteDTOinput.getNome());
		restaurante.setTaxaFrete(restauranteDTOinput.getTaxaFrete());
		
		Gastronomia cozinha = new Gastronomia();
		cozinha.setId(restauranteDTOinput.getCozinha().getId());
		
		restaurante.setCozinha(cozinha);
		
		return restaurante;
	}
	
	public RestauranteDTOinput toDTOinput(Restaurante restaurante) {
		RestauranteDTOinput restauranteDTOinput = new RestauranteDTOinput();
		restauranteDTOinput.setNome(restaurante.getNome());
		restauranteDTOinput.setTaxaFrete(restaurante.getTaxaFrete());
		CozinhaDTOinputRef cozinha = new CozinhaDTOinputRef();
		cozinha.setId(restaurante.getCozinha().getId());
		restauranteDTOinput.setCozinha(cozinha);
		return restauranteDTOinput;
	}
}
