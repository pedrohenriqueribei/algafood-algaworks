package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "tb_produtos")
public class Produto {		
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "produto_nome")
	private String nome;
	
	@Column(name = "produto_descricao")
	private String descricao;
	
	@Column(name = "produto_preco")
	private BigDecimal preco;
	
	private Boolean ativo;
	
	@ManyToOne
	private Restaurante restaurante;

}
