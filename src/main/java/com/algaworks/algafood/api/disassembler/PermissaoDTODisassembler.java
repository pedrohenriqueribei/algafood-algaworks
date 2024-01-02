package com.algaworks.algafood.api.disassembler;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.DTO.input.PermissaoDTOinput;
import com.algaworks.algafood.domain.model.Permissao;

@Component
public class PermissaoDTODisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Permissao toDomainObject(PermissaoDTOinput permissaoDTODinput) {
		return modelMapper.map(permissaoDTODinput, Permissao.class);
	}

	public void copiarParaDominio(@Valid PermissaoDTOinput permissaoDTOinput, Permissao permissao) {
		modelMapper.map(permissaoDTOinput, permissao);
	}
}
