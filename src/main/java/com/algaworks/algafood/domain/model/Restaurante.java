package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

//import com.fasterxml.jackson.annotation.JsonIgnore;

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
	
//	@JsonIgnore
	@ManyToOne //muitos restaurantes possui uma cozinha / uma cozinha possui muitos restaurantes
	@JoinColumn(name = "cozinha_id", nullable = false)
	private Cozinha cozinha;
	
	@Embedded
	private Endereco endereco;
	
	/*
	 * a anotação @CreationTimestamp informa que a propriedade anotada deve ser atribuida com data e hora local do momento em que o objeto for criado
	 * é do hibernate
	 */
	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private LocalDateTime dataCadastro;
	
	/*
	 * a anotação @UpdateTimestamp informa que a data e hora atual deve ser atribuida a propriedade anotada sempre que a entidade for atualizada
	 */
	@UpdateTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private LocalDateTime dataAtualizacao;
	
	
	@ManyToMany
	@JoinTable(name = "tb_restaurante_forma_pagamento", 
		joinColumns = @JoinColumn(name = "restaurante_id"),
		inverseJoinColumns = @JoinColumn(name ="forma_pagamento_id"))
	private List<FormaPagamento> formasPagamento = new ArrayList<>();
	
	
	
	@Override
	public String toString() {
		return "Restaurante\n"+
				"ID:   "+getId()+"\n"+
				"Nome: "+getNome()+"\n"+
				"Taxa: R$ "+getTaxaFrete()+"\n"+
				"Cozinha: "+getCozinha().getNome();
	}
}
