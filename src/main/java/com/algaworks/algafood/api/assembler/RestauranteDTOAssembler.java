package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.DTO.output.RestauranteDTO;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteDTOAssembler {
	
	@Autowired
	private ModelMapper modelMapper;

	
	public RestauranteDTO toDTO(Restaurante restaurante) {
		return modelMapper.map(restaurante, RestauranteDTO.class);
	}
	
	public List<RestauranteDTO> toCollectionDTO(List<Restaurante> restaurantes){
		return restaurantes.stream()
				.map(restaurante -> toDTO(restaurante))
				.collect(Collectors.toList());
	}
}

//public RestauranteDTO toDTO(Restaurante restaurante) {
//	CozinhaDTO cozinhaDTO = new CozinhaDTO();
//	cozinhaDTO.setId(restaurante.getCozinha().getId());
//	cozinhaDTO.setNome(restaurante.getCozinha().getNome());
//	
//	RestauranteDTO restauranteDTO = new RestauranteDTO();
//	restauranteDTO.setId(restaurante.getId());
//	restauranteDTO.setNome(restaurante.getNome());
//	restauranteDTO.setTaxaFrete(restaurante.getTaxaFrete());
//	restauranteDTO.setCozinha(cozinhaDTO);
//	return restauranteDTO;
//}
