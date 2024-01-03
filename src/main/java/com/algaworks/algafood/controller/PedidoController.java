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
import com.algaworks.algafood.api.model.DTO.input.PedidoInputDTO;
import com.algaworks.algafood.api.model.DTO.output.PedidoDTO;
import com.algaworks.algafood.api.model.DTO.output.PedidoResumoDTO;
import com.algaworks.algafood.domain.model.Pedido;
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
			PedidoInputDTO pedidoInputDTO) {
		
		Pedido pedido = pedidoDisassembler.toDomainObject(pedidoInputDTO);
		
//		try {
//			pedido = cadastroPedidoService.salvar(pedido);
//			
//		} catch (EntidadeNaoEncontradaException e) {
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
		return pedidoAssembler.toDTO(pedido);
	}
	
}
