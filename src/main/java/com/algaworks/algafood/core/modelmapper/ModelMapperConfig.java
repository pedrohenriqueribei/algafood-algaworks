package com.algaworks.algafood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();
		
//		modelMapper.createTypeMap(Restaurante.class, RestauranteDTO.class).addMapping(Restaurante::getTaxaFrete, RestauranteDTO::setTaxaFrete);
		
//		modelMapper.createTypeMap(ItemPedidoDTOinput.class, ItemPedido.class)
//	    	.addMappings(mapper -> mapper.skip(ItemPedido::setId));  
//		
//		var enderecoToEnderecoModelTypeMap = modelMapper.createTypeMap(
//				Endereco.class, EnderecoDTO.class);
//		
//		enderecoToEnderecoModelTypeMap.<String>addMapping(
//				enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(),
//				(enderecoModelDest, value) -> enderecoModelDest.getCidade().setNomeEstado(value));
		
		return modelMapper;
	}
}
