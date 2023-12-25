package com.algaworks.algafood.api.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.DTO.input.EstadoDTOinput;
import com.algaworks.algafood.domain.model.Estado;

@Component
public class EstadoDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Estado paraDominio (EstadoDTOinput estadoDTOinput) {
		Estado estado = modelMapper.map(estadoDTOinput, Estado.class);
		
		return estado;
	}
	
	public void copiarParaDominio (EstadoDTOinput estadoDTOinput, Estado estado) {
		modelMapper.map(estadoDTOinput, estado);
	}
}
