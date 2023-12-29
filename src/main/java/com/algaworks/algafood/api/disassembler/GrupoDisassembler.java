package com.algaworks.algafood.api.disassembler;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.DTO.input.GrupoDTOinput;
import com.algaworks.algafood.domain.model.Grupo;

@Component
public class GrupoDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Grupo toDomain (GrupoDTOinput dtOinput) {
		return modelMapper.map(dtOinput, Grupo.class);
	}

	public void copiarParaDominio(@Valid GrupoDTOinput grupoDTOinput, Grupo grupo) {
		modelMapper.map(grupoDTOinput, grupo);
	}
}
