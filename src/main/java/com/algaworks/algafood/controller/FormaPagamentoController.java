package com.algaworks.algafood.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.FormaPagamentoAssembler;
import com.algaworks.algafood.api.disassembler.FormaPagamentoDisassembler;
import com.algaworks.algafood.api.model.DTO.input.FormaPagamentoDTOinput;
import com.algaworks.algafood.api.model.DTO.output.FormaPagamentoDTO;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafood.domain.service.CadastroFormaPagamentoService;

@RestController
@RequestMapping("formaspagamento")
public class FormaPagamentoController {
	
	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;
	
	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamentoService;
	
	@Autowired
	private FormaPagamentoAssembler pagamentoAssembler;
	
	@Autowired
	private FormaPagamentoDisassembler pagamentoDisassembler;
	
	@GetMapping
	public List<FormaPagamentoDTO> listar (){
		return pagamentoAssembler.toCollectDTO(formaPagamentoRepository.findAll());
	}

	@GetMapping("{id}")
	public FormaPagamentoDTO busca (@PathVariable Long id) {
		FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscarOuFalhar(id);
		return pagamentoAssembler
				.toDTO(formaPagamento);
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public FormaPagamentoDTO adicionar(
			@RequestBody 
			@Valid
			FormaPagamentoDTOinput formaPgInput) {
		
		FormaPagamento formaPagamento = pagamentoDisassembler.toDomainObject(formaPgInput);
		return pagamentoAssembler.toDTO(cadastroFormaPagamentoService.salvar(formaPagamento));
		
	}
	
	@PutMapping("{id}")
	public FormaPagamentoDTO atualizar (
			@PathVariable Long id, 
			@RequestBody 
			@Valid 
			FormaPagamentoDTOinput formaPgInput){
		
		FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscarOuFalhar(id);
		
		pagamentoDisassembler.copyToDomainObject(formaPgInput,  formaPagamento);
		
		return pagamentoAssembler.toDTO(cadastroFormaPagamentoService.salvar(formaPagamento)); 
		
	}
	
	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long id) {
		cadastroFormaPagamentoService.excluir(id);
	}
	
	

}
