package com.algaworks.algafood.api.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.DTO.input.CidadeDTOinput;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;

@Component
public class CidadeDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Cidade deDTOparaModelo(CidadeDTOinput cidadeDTOinput) {
		Cidade cidade = modelMapper.map(cidadeDTOinput, Cidade.class);
		return cidade;
	}
	
	public void copiarParaDominio (CidadeDTOinput cidadeDTOinput, Cidade cidade) {
		cidade.setEstado(new Estado());
		modelMapper.map(cidadeDTOinput, cidade);
	}
}
