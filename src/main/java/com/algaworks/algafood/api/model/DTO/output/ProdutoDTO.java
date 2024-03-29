package com.algaworks.algafood.api.model.DTO.output;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProdutoDTO {

	private Long id;
	private String descricao;
	private String nome;
	private BigDecimal preco;
	private Boolean ativo;
}
