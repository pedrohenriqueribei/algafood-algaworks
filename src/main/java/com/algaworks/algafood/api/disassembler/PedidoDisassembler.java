package com.algaworks.algafood.api.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.DTO.input.PedidoDTOinput;
import com.algaworks.algafood.domain.model.Pedido;

@Component
public class PedidoDisassembler {

	@Autowired
	private ModelMapper modelMapper;

	public Pedido toDomainObject(PedidoDTOinput pedidoInputDTO) {
		return modelMapper.map(pedidoInputDTO, Pedido.class);
	}
	
	public void copyToDomainObject(PedidoDTOinput pedidoDTOinput, Pedido pedido) {
		modelMapper.map(pedidoDTOinput, pedido);
	}
}
