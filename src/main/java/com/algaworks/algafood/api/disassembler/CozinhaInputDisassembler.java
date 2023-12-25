package com.algaworks.algafood.api.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.DTO.input.CozinhaDTOinput;
import com.algaworks.algafood.domain.model.Cozinha;

@Component
public class CozinhaInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Cozinha deDTOparaCozinha(CozinhaDTOinput cozinhaDTOinput) {
		return modelMapper.map(cozinhaDTOinput, Cozinha.class);
	}
	
	public void copiarDeDTOparaModelo (CozinhaDTOinput cozinhaDTOinput, Cozinha cozinha) {
		modelMapper.map(cozinhaDTOinput, cozinha);
	}
}
