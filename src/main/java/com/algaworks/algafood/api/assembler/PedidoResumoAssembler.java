package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.DTO.output.PedidoResumoDTO;
import com.algaworks.algafood.domain.model.Pedido;

@Component
public class PedidoResumoAssembler {
	
	@Autowired
	private ModelMapper modelMapper;

	public PedidoResumoDTO toDTO(Pedido pedido) {
		return modelMapper.map(pedido, PedidoResumoDTO.class);
	}
	
	public List<PedidoResumoDTO> toCollectDTO(List<Pedido> pedidos) {
		return pedidos.stream()
				.map(pedido -> toDTO(pedido))
				.collect(Collectors.toList());
	}
}
