package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.PedidoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.PedidoRepository;

@Service
public class CadastroPedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	public List<Pedido> listar(){
		return pedidoRepository.findAll();
	}
	
	public Pedido buscarOuFalhar(Long id) {
		return pedidoRepository.findById(id).orElseThrow(() -> new PedidoNaoEncontradoException(id));
	}

}


//public Restaurante buscarOuFalhar (Long restauranteId) {
//	return restauranteRepository.findById(restauranteId).orElseThrow(() -> new RestauranteNaoEncontradoException(restauranteId));
//}