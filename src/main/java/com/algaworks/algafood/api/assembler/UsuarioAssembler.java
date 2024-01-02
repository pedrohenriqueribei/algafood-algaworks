package com.algaworks.algafood.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.DTO.output.UsuarioDTO;
import com.algaworks.algafood.domain.model.Usuario;

@Component
public class UsuarioAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public UsuarioDTO toDTO (Usuario usuario) {
		return modelMapper.map(usuario, UsuarioDTO.class);
	}
	
	public List<UsuarioDTO> toCollectDTO (Collection<Usuario> usuarios){
		return usuarios.stream()
				.map(user -> toDTO(user))
				.collect(Collectors.toList());
	}
}
