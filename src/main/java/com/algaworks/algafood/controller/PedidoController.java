package com.algaworks.algafood.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.PedidoAssembler;
import com.algaworks.algafood.api.assembler.PedidoResumoAssembler;
import com.algaworks.algafood.api.disassembler.PedidoDisassembler;
import com.algaworks.algafood.api.model.DTO.input.PedidoDTOinput;
import com.algaworks.algafood.api.model.DTO.output.PedidoDTO;
import com.algaworks.algafood.api.model.DTO.output.PedidoResumoDTO;
import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.FormaPagamentoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.CadastroPedidoService;

@RestController
@RequestMapping("pedidos")
public class PedidoController {

	@Autowired
	private CadastroPedidoService cadastroPedidoService;
	
	@Autowired
	private PedidoAssembler pedidoAssembler;
	
	@Autowired
	private PedidoResumoAssembler pedidoResumoAssembler;
	
	@Autowired
	private PedidoDisassembler pedidoDisassembler;
	
	@GetMapping
	public List<PedidoResumoDTO> listar(){
		return pedidoResumoAssembler.toCollectDTO(cadastroPedidoService.listar());
	}
	
	@GetMapping("{pedidoId}")
	public PedidoDTO buscar(@PathVariable Long pedidoId){
		return pedidoAssembler.toDTO(cadastroPedidoService.buscarOuFalhar(pedidoId));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoDTO incluir (
			@RequestBody 
			@Valid
			PedidoDTOinput pedidoInputDTO) {
		
		
		try {
			Pedido pedido = pedidoDisassembler.toDomainObject(pedidoInputDTO);
			pedido.setCliente(new Usuario());
			pedido.getCliente().setId(1L);
//			pedido = cadastroPedidoService.salvar(pedido);
			
			System.out.println();
			System.out.println("Itens Produto Nome");
			System.out.println(pedido.getItens().get(0).getProduto().getDescricao());
			
			System.out.println("Itens Cliente Nome");
			System.out.println(pedido.getCliente().getNome());
			
			return pedidoAssembler.toDTO(cadastroPedidoService.salvar(pedido));
		} catch (RestauranteNaoEncontradoException | FormaPagamentoNaoEncontradoException | CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);	
		}
	}
	
}
