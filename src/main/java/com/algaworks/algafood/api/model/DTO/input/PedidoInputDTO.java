package com.algaworks.algafood.api.model.DTO.input;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.algaworks.algafood.domain.model.Endereco;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.StatusPedido;
import com.algaworks.algafood.domain.model.Usuario;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoInputDTO {

	private BigDecimal subtotal;
	private BigDecimal taxaFrete;
	private BigDecimal valorTotal;
	private StatusPedido statusPedido = StatusPedido.CRIADO;
	private Endereco enderecoEntrega;
	private OffsetDateTime dataCriacao;
	private OffsetDateTime dataConfirmacao;
	private OffsetDateTime dataCancelamento;
	private OffsetDateTime dataEntrega;
	private FormaPagamento formaPagamento;
	private Usuario cliente;
	private Restaurante restaurante;
}
