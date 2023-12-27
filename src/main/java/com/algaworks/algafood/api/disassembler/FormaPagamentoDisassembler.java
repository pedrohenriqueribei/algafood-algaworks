package com.algaworks.algafood.api.disassembler;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.DTO.input.FormaPagamentoDTOinput;
import com.algaworks.algafood.domain.model.FormaPagamento;

@Component
public class FormaPagamentoDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public FormaPagamento toDomainObject(FormaPagamentoDTOinput dtOinput) {
		return modelMapper.map(dtOinput, FormaPagamento.class);
	}

	public void copyToDomainObject(@Valid FormaPagamentoDTOinput formaPgInput, FormaPagamento formaPagamento) {
		modelMapper.map(formaPgInput, formaPagamento);
		
	}
}
