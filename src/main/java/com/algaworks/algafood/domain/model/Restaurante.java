package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "tb_restaurantes")
public class Restaurante {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	private String nome;
	private BigDecimal taxaFrete;
	
	//@JsonIgnore
	@ManyToOne //muitos restaurantes possui uma cozinha / uma cozinha possui muitos restaurantes
	@JoinColumn(name = "cozinha_id", nullable = false)
	private Cozinha cozinha;
	
	@Override
	public String toString() {
		return "Restaurante\n"+
				"ID:   "+getId()+"\n"+
				"Nome: "+getNome()+"\n"+
				"Taxa: R$ "+getTaxaFrete()+"\n"+
				"Cozinha: "+getCozinha().getNome();
	}
}
