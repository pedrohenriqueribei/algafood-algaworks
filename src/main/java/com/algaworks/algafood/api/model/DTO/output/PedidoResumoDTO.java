package com.algaworks.algafood.api.model.DTO.output;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoResumoDTO {

	private Long id;
	private BigDecimal subtotal;
	private BigDecimal taxaFrete;
	private BigDecimal valorTotal;
	private String statusPedido;
	private OffsetDateTime dataCriacao;
	private FormaPagamentoDTO formaPagamento;
	private UsuarioDTO cliente;
	private RestauranteResumidoDTO restaurante;
}
