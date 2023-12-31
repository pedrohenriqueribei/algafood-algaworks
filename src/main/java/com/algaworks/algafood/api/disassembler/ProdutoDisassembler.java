package com.algaworks.algafood.api.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.DTO.input.ProdutoDTOinput;
import com.algaworks.algafood.domain.model.Produto;

@Component
public class ProdutoDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Produto toDomainObject(ProdutoDTOinput input) {
		Produto produto = modelMapper.map(input, Produto.class);
		return produto;
	}
	
	public void copiarParaDominio(ProdutoDTOinput produtoDTOinput, Produto produto) {
		modelMapper.map(produtoDTOinput, produto);
	}
}
