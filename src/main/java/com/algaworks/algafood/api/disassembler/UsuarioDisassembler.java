package com.algaworks.algafood.api.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.DTO.input.UsuarioDTOinput;
import com.algaworks.algafood.domain.model.Usuario;

@Component
public class UsuarioDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Usuario toDomainObject (UsuarioDTOinput usuarioDTOinput) {
		return modelMapper.map(usuarioDTOinput, Usuario.class);
	}
	
	public void copiarParaDominio(UsuarioDTOinput dtOinput, Usuario usuario) {
		modelMapper.map(dtOinput, usuario);
	}
}
