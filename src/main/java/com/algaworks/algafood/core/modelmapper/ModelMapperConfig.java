package com.algaworks.algafood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algafood.api.model.DTO.input.ItemPedidoDTOinput;
import com.algaworks.algafood.domain.model.ItemPedido;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();
		
		modelMapper.createTypeMap(ItemPedidoDTOinput.class, ItemPedido.class)
        .addMappings(mapper -> mapper.skip(ItemPedido::setId));
		
//		modelMapper.createTypeMap(Restaurante.class, RestauranteDTO.class).addMapping(Restaurante::getTaxaFrete, RestauranteDTO::setTaxaFrete);
		
		return modelMapper;
	}
}
