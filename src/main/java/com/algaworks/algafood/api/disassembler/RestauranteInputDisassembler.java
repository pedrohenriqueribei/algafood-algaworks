package com.algaworks.algafood.api.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.DTO.input.RestauranteDTOinput;
import com.algaworks.algafood.domain.model.Gastronomia;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;

	public Restaurante toDomainObject(RestauranteDTOinput restauranteDTOinput) {
		
		return modelMapper.map(restauranteDTOinput, Restaurante.class);
	}
	
	public RestauranteDTOinput toDTOinput(Restaurante restaurante) {
		return modelMapper.map(restaurante, RestauranteDTOinput.class);
	}
	
	public void copyToDomainObject (RestauranteDTOinput restauranteDTOinput, Restaurante restaurante) {
		restaurante.setCozinha(new Gastronomia());
		modelMapper.map(restauranteDTOinput, restaurante);
	}
}
