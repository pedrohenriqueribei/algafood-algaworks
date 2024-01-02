package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.DTO.output.PedidoDTO;
import com.algaworks.algafood.domain.model.Pedido;

@Component
public class PedidoAssembler {
	
	@Autowired
	private ModelMapper modelMapper;

	public PedidoDTO toDTO(Pedido pedido) {
		return modelMapper.map(pedido, PedidoDTO.class);
	}
	
	public List<PedidoDTO> toCollectDTO(List<Pedido> pedidos) {
		return pedidos.stream()
				.map(pedido -> toDTO(pedido))
				.collect(Collectors.toList());
	}
}
