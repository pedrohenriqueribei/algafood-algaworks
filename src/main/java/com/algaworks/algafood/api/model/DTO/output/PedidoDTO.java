package com.algaworks.algafood.api.model.DTO.output;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoDTO {
	
	private Long id;
	private BigDecimal subtotal;
	private BigDecimal taxaFrete;
	private BigDecimal valorTotal;
	private String statusPedido;
	private EnderecoDTO enderecoEntrega;
	private OffsetDateTime dataCriacao;
	private OffsetDateTime dataConfirmacao;
	private OffsetDateTime dataCancelamento;
	private OffsetDateTime dataEntrega;
	private FormaPagamentoDTO formaPagamento;
	private UsuarioDTO cliente;
	private RestauranteResumidoDTO restaurante;
	private List<ItemPedidoDTO> itensPedido;
}
