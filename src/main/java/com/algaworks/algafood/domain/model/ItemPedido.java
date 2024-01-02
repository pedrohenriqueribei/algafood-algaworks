package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "tb_item_pedido")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ItemPedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	
	private Integer quantidade;
	private BigDecimal precoUnitario;
	private BigDecimal precoTotal;
	
	@NotNull
	private String observacao;
	
	@ManyToOne
    @JoinColumn(nullable = false)
    private Pedido pedido;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Produto produto;
}
