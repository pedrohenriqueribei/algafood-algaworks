package com.algaworks.algafood.domain.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.PedidoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.ItemPedido;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.PedidoRepository;

@Service
public class CadastroPedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;
	
	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamentoService;
	
	@Autowired
	private CadastroCidadeService cadastroCidadeService;
	
	@Autowired
	private CadastroUsuarioService cadastroUsuarioService;
	
	@Autowired
	private CadastroProdutoService cadastroProdutoService;
	
	@Transactional
	public Pedido salvar(Pedido pedido) {
		
		validarPedido(pedido);
		validarItens(pedido);
		
		pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
		pedido.calcularValorTotal();
		
		
		return pedidoRepository.save(pedido);
	}
	
	public List<Pedido> listar(){
		return pedidoRepository.findAll();
	}
	
	public Pedido buscarOuFalhar(Long id) {
		return pedidoRepository.findById(id).orElseThrow(() -> new PedidoNaoEncontradoException(id));
	}
	
	private void validarPedido(Pedido pedido) {
		Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(pedido.getRestaurante().getId());
		FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscarOuFalhar(pedido.getFormaPagamento().getId());
		Cidade cidade = cadastroCidadeService.buscarOufahar(pedido.getEnderecoEntrega().getCidade().getId());
		Usuario usuario = cadastroUsuarioService.buscarOuFalhar(pedido.getCliente().getId());
		
		pedido.setRestaurante(restaurante);
		pedido.setFormaPagamento(formaPagamento);
		pedido.getEnderecoEntrega().setCidade(cidade);
		pedido.setCliente(usuario);
		
		if(restaurante.naoAceitaFormaPagamento(formaPagamento)){
			throw new NegocioException(String.format("Forma de pagamento '%s' não é aceita por esse restaurante.", formaPagamento.getDescricao()));
		}
	}
	
	private void validarItens(Pedido pedido) {
		for (ItemPedido item : pedido.getItens()) {
			Produto produto = cadastroProdutoService.buscarOuFalhar(pedido.getRestaurante().getId(), item.getProduto().getId());
			
			item.setPedido(pedido);
			item.setProduto(produto);
			item.setPrecoUnitario(produto.getPreco());
		}
	}

}