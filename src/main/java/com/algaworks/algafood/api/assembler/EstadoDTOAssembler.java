package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.DTO.output.EstadoDTO;
import com.algaworks.algafood.domain.model.Estado;

@Component
public class EstadoDTOAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public EstadoDTO toDTO (Estado estado) {
		
		EstadoDTO dto = modelMapper.map(estado, EstadoDTO.class);
		
		return dto;
	}
	
	public List<EstadoDTO> toCollectDTO (List<Estado> estados){
		return estados.stream()
				.map(estado -> toDTO(estado))
				.collect(Collectors.toList());
	}
}
