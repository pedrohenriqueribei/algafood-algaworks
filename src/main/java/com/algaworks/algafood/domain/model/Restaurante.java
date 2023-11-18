package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.algaworks.algafood.core.validation.TaxaFrete;
import com.algaworks.algafood.valid.groups.Groups;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "tb_restaurante")
public class Restaurante {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	
	//@NotNull não pode ser nulo
	//@NotEmpty não pode vazio e nem nulo
	@NotBlank //não pode ser vazio, não pode ser nulo e não pode ter espaço em branco
	private String nome;
	
//	@DecimalMin("0")
//	@PositiveOrZero
	@TaxaFrete
	@NotNull
	@Column(name = "taxa_frete", nullable = false)
	private BigDecimal taxaFrete;
	
	@Valid
	@ConvertGroup(from = Default.class, to = Groups.CozinhaId.class)
	@NotNull
	@JsonIgnoreProperties("hibernateLazyInitializer")
	@ManyToOne(fetch = FetchType.LAZY) //muitos restaurantes possui uma cozinha / uma cozinha possui muitos restaurantes
	@JoinColumn(name = "cozinha_id", nullable = false)
	private Gastronomia cozinha;
	
	@JsonIgnore
	@Embedded
	private Endereco endereco;
	
	/*
	 * a anotação @CreationTimestamp informa que a propriedade anotada deve ser atribuida com data e hora local do momento em que o objeto for criado
	 * é do hibernate
	 */
	@JsonIgnore
	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private LocalDateTime dataCadastro;
	
	/*
	 * a anotação @UpdateTimestamp informa que a data e hora atual deve ser atribuida a propriedade anotada sempre que a entidade for atualizada
	 */
	@JsonIgnore
	@UpdateTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private LocalDateTime dataAtualizacao;
	
	
	@ManyToMany (fetch = FetchType.EAGER)
	@JoinTable(name = "tb_restaurante_forma_pagamento", 
		joinColumns = @JoinColumn(name = "restaurante_id"),
		inverseJoinColumns = @JoinColumn(name ="forma_pagamento_id"))
	private List<FormaPagamento> formasPagamento = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "restaurante")
	private List<Produto> produtos = new ArrayList<>();
	
	@Override
	public String toString() {
		return "Restaurante\n"+
				"ID:   "+getId()+"\n"+
				"Nome: "+getNome()+"\n"+
				"Taxa: R$ "+getTaxaFrete()+"\n"+
				"Cozinha: "+getCozinha().getNome();
	}
}
