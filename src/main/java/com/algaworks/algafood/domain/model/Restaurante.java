package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.algaworks.algafood.core.validation.FreteGratis;
import com.algaworks.algafood.core.validation.TaxaFrete;
import com.algaworks.algafood.valid.groups.Groups;

//import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@FreteGratis(valorField = "taxaFrete", descricaoField = "nome", descricaoObrigatoria="Frete Grátis")
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
	@TaxaFrete //criação de uma validação com notnull e positiveOrZero
//	@Multiplo(numero = 5) só um exemplo para criar validação de frete multiplo de 5
	@Column(name = "taxa_frete", nullable = false)
	private BigDecimal taxaFrete;
	
	@Valid
	@ConvertGroup(from = Default.class, to = Groups.CozinhaId.class)
	@NotNull
	@ManyToOne//(fetch = FetchType.LAZY) //muitos restaurantes possui uma cozinha / uma cozinha possui muitos restaurantes
	@JoinColumn(name = "cozinha_id", nullable = false)
	private Cozinha cozinha;
	
	@Embedded
	private Endereco endereco;
	
	private Boolean ativo = Boolean.TRUE;
	
	private Boolean aberto = Boolean.FALSE;
	
	/*
	 * a anotação @CreationTimestamp informa que a propriedade anotada deve ser atribuida com data e hora local do momento em que o objeto for criado
	 * é do hibernate
	 */
	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime dataCadastro;
	
	/*
	 * a anotação @UpdateTimestamp informa que a data e hora atual deve ser atribuida a propriedade anotada sempre que a entidade for atualizada
	 */
	@UpdateTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime dataAtualizacao;
	
	
	@ManyToMany (fetch = FetchType.EAGER)
	@JoinTable(name = "tb_restaurante_forma_pagamento", 
		joinColumns = @JoinColumn(name = "restaurante_id"),
		inverseJoinColumns = @JoinColumn(name ="forma_pagamento_id"))
	private Set<FormaPagamento> formasPagamento = new HashSet<>();
	
	@OneToMany(mappedBy = "restaurante")
	private List<Produto> produtos = new ArrayList<>();
	
	@ManyToMany
	@JoinTable(name = "tb_restaurante_usuarios_responsaveis",
			joinColumns = @JoinColumn(name = "restaurante_id"),
			inverseJoinColumns = @JoinColumn(name = "usuario_id"))
	private Set<Usuario> usuarios = new HashSet<>();
	
	@Override
	public String toString() {
		return "Restaurante\n"+
				"ID:   "+getId()+"\n"+
				"Nome: "+getNome()+"\n"+
				"Taxa: R$ "+getTaxaFrete()+"\n"+
				"Cozinha: "+getCozinha().getNome();
	}
	
	public void ativar() {
		setAtivo(true);
	}
	
	public void inativar() {
		setAtivo(false);
	}
	
	public boolean adicionarFormaPagamento (FormaPagamento formaPagamento) {
		return getFormasPagamento().add(formaPagamento);
	}
	
	public boolean removerFormaPagamento (FormaPagamento formaPagamento) {
		return getFormasPagamento().remove(formaPagamento);
	}
	
	public void abrir() {
		setAberto(true);
	}
	
	public void fechar() {
		setAberto(false);
	}
	
	public boolean adicionarResponsavel(Usuario usuario) {
		return getUsuarios().add(usuario);
	}
	
	public boolean removerResponsavel(Usuario usuario) {
		return getUsuarios().remove(usuario);
	}
	
	public boolean aceitaFormaPagamento(FormaPagamento formaPagamento) {
	    return getFormasPagamento().contains(formaPagamento);
	}

	public boolean naoAceitaFormaPagamento(FormaPagamento formaPagamento) {
	    return !aceitaFormaPagamento(formaPagamento);
	}
}
