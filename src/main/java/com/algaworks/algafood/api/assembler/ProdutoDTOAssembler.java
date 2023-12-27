package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.DTO.input.ProdutoDTOinput;
import com.algaworks.algafood.api.model.DTO.output.ProdutoDTO;
import com.algaworks.algafood.domain.model.Produto;

@Component
public class ProdutoDTOAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public ProdutoDTO paraDTO(Produto produto) {
		return modelMapper.map(produto, ProdutoDTO.class);
	}
	
	public List<ProdutoDTO> paraColecaoDTO (List<Produto> produtos){
		return produtos.stream()
				.map(produto -> paraDTO(produto))
				.collect(Collectors.toList());
	}
	
	public Produto toDomainObject(ProdutoDTOinput input) {
		Produto produto = modelMapper.map(input, Produto.class);
		return produto;
	}
}
