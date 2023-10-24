package com.algaworks.algafood.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("restaurantes")
public class RestauranteController {
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;

	@GetMapping
	public List<Restaurante> listar () {
		return restauranteRepository.findAll();
	}
	
	@GetMapping("{id}")
	public Restaurante buscar (@PathVariable Long id) {
		
		return cadastroRestauranteService.buscarOuFalhar(id);
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Restaurante adicionar (@RequestBody Restaurante restaurante) {
		
		try {
			return cadastroRestauranteService.salvar(restaurante);			
		} catch (CozinhaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	@PutMapping("{id}")
	public Restaurante atualizar (@PathVariable Long id,@RequestBody Restaurante restaurante){
		
			Restaurante restauranteAtual = cadastroRestauranteService.buscarOuFalhar(id);
			
			//copie os dados de restaurante para restauranteAtual e ignore os campos: id e formasPagamento
			BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "endereco", "dataCadastro", "produtos");
			
			try {
				return cadastroRestauranteService.salvar(restauranteAtual);
			} catch (CozinhaNaoEncontradaException e) {
				throw new NegocioException(e.getMessage());
			}
	}
	
	@PatchMapping("{id}")
	public Restaurante atualizarParcial (@PathVariable Long id, @RequestBody Map<String, Object> campos) {
		
		Restaurante restauranteAtual = cadastroRestauranteService.buscarOuFalhar(id);
		
		merge(campos, restauranteAtual);
		
		return atualizar(id, restauranteAtual);
	}

	private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino) {
		ObjectMapper objectMapper = new ObjectMapper();
		Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);
		
		dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
			Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
			field.setAccessible(true);
			
			Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
			
			System.out.println(nomePropriedade + " = "+ valorPropriedade + " = " + novoValor);
			
			ReflectionUtils.setField(field, restauranteDestino, novoValor);
			
		});
	}
	
	/*
	 * código antigo
	 */
//	@GetMapping("{id}")
//	public ResponseEntity<Restaurante> buscar (@PathVariable Long id) {
//		
//		Restaurante restaurante = restauranteRepository.findById(id).get();
//		
//		if(restaurante != null) {
//			return ResponseEntity.ok(restaurante);
//		}
//		
//		return ResponseEntity.notFound().build();
//	}
//	@PutMapping("{id}")
//	public ResponseEntity<?> atualizar (@PathVariable Long id,@RequestBody Restaurante restaurante){
//		
//		try {
//			Restaurante restauranteAtual = restauranteRepository.findById(id).get();
//			
//			if(restauranteAtual != null) {
//
//				//copie os dados de resstaurante para restauranteAtual e ignore os campos: id e formasPagamento
//				BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "endereco", "dataCadastro", "produtos");
//				restauranteAtual = cadastroRestauranteService.salvar(restauranteAtual);
//				return ResponseEntity.ok(restauranteAtual);
//
//			}
//			//se o restaurante não for encontrado
//			return ResponseEntity.notFound().build();
//			
//		} catch (EntidadeNaoEncontradaException e) {
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
//	}
}
