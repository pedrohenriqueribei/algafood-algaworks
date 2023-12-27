package com.algaworks.algafood.api.model.DTO.input;

import java.math.BigDecimal;

import com.algaworks.algafood.domain.model.Restaurante;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoDTOinput {

private String nome;
	
	private String descricao;
	private BigDecimal preco;
	private Boolean ativo;
	private Restaurante restaurante;
}
