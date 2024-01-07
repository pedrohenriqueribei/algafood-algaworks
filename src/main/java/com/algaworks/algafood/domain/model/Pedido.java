package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name="tb_pedido")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Pedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	
	private BigDecimal subtotal = BigDecimal.ZERO;
	private BigDecimal taxaFrete = BigDecimal.ZERO;
	private BigDecimal valorTotal = BigDecimal.ZERO;
	
	@Enumerated(EnumType.STRING)
	private StatusPedido statusPedido = StatusPedido.CRIADO;

	@Embedded
	private Endereco enderecoEntrega;
	
	@CreationTimestamp
	@Column(columnDefinition = "datetime")
	private OffsetDateTime dataCriacao;
	
	@Column(columnDefinition = "datetime")
	private OffsetDateTime dataConfirmacao;
	
	@Column(columnDefinition = "datetime")
	private OffsetDateTime dataCancelamento;
	
	@Column(columnDefinition = "datetime")
	private OffsetDateTime dataEntrega;
	
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private FormaPagamento formaPagamento;
	
	
	@ManyToOne
	@JoinColumn(name="usuario_id", nullable = false)
	private Usuario cliente;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Restaurante restaurante;
	
	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
	private List<ItemPedido> itens = new ArrayList<>();
	
	public void calcularValorTotal() {
	    this.subtotal = getItens().stream()
	        .map(item -> item.getPrecoTotal())
	        .reduce(BigDecimal.ZERO, BigDecimal::add);
	    
	    this.valorTotal = this.subtotal.add(this.taxaFrete);
	}

	public void definirFrete() {
	    setTaxaFrete(getRestaurante().getTaxaFrete());
	}

	public void atribuirPedidoAosItens() {
	    getItens().forEach(item -> item.setPedido(this));
	}
}
