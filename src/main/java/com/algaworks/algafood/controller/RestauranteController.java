package com.algaworks.algafood.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.RestauranteDTOAssembler;
import com.algaworks.algafood.api.disassembler.RestauranteInputDisassembler;
import com.algaworks.algafood.api.model.DTO.input.RestauranteDTOinput;
import com.algaworks.algafood.api.model.DTO.output.RestauranteDTO;
import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.ValidacaoException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("restaurantes")
public class RestauranteController {
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;
	
	@Autowired
	private SmartValidator smartValidator;
	
	@Autowired
	private RestauranteDTOAssembler restauranteDTOAssembler;
	
	@Autowired
	private RestauranteInputDisassembler restauranteInputDisassembler;

	@GetMapping
	public List<RestauranteDTO> listar () {
		return restauranteDTOAssembler.toCollectionDTO(restauranteRepository.findAll());
	}
	
	@GetMapping("{id}")
	public RestauranteDTO buscar (@PathVariable Long id) {
		
		Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(id);
		
		RestauranteDTO restauranteDTO = restauranteDTOAssembler.toDTO(restaurante);
		
		return restauranteDTO;
	}

	
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public RestauranteDTO adicionar (
			@RequestBody 
			@Valid
			RestauranteDTOinput restauranteInput) {
		
		try {
			Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInput);
			return restauranteDTOAssembler.toDTO(cadastroRestauranteService.salvar(restaurante));			
		} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	@PutMapping("{id}")
	public RestauranteDTO atualizar (@PathVariable Long id, 
			@RequestBody 
			@Valid 
			RestauranteDTOinput restauranteInput){
		
		try {
			//não precisa mais criar um restaurante a partir do restaurantedtoinput
			//Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInput);
			
			Restaurante restauranteAtual = cadastroRestauranteService.buscarOuFalhar(id);
			restauranteInputDisassembler.copyToDomainObject(restauranteInput, restauranteAtual);
			
			//o dto restauranteDTOinput só tem os atributos corretos, então não tem atrubuto null para ignorar
			//copie os dados de restaurante para restauranteAtual e ignore os campos: id e formasPagamento
			//BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "endereco", "dataCadastro", "produtos");
			
			return restauranteDTOAssembler.toDTO(cadastroRestauranteService.salvar(restauranteAtual));
		} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	@PatchMapping("{id}")
	public RestauranteDTO atualizarParcial (@PathVariable Long id, @RequestBody Map<String, Object> campos, HttpServletRequest servletRequest) {
		
		Restaurante restauranteAtual = cadastroRestauranteService.buscarOuFalhar(id);
		
		merge(campos, restauranteAtual, servletRequest);
		
		//9.19. Executando processo de validação programaticamente
		validate(restauranteAtual, "restaurante");
		
		return atualizar(id, restauranteInputDisassembler.toDTOinput(restauranteAtual));
	}

	private void validate(Restaurante restaurante, String objectName) {

		BeanPropertyBindingResult beanPropertyBindingResult = new BeanPropertyBindingResult(restaurante, objectName);
		
		smartValidator.validate(restaurante, beanPropertyBindingResult);
		
		if(beanPropertyBindingResult.hasErrors()) {
			throw new ValidacaoException(beanPropertyBindingResult);
		}
	}

	private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino, HttpServletRequest servletRequest) {
		
		ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(servletRequest);
		
		try {	
			ObjectMapper objectMapper = new ObjectMapper();
			
			//8.24. Lançando exception de desserialização na atualização parcial (PATCH)
			objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
			
			Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);
			
			dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
				Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
				field.setAccessible(true);
				
				Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
				
				System.out.println(nomePropriedade + " = "+ valorPropriedade + " = " + novoValor);
				
				ReflectionUtils.setField(field, restauranteDestino, novoValor);
				
			});
		} catch(IllegalArgumentException e) {
			Throwable rootCause = ExceptionUtils.getRootCause(e);
			throw new HttpMessageNotReadableException(e.getMessage(), rootCause,  serverHttpRequest);
		}
	}
	
	//PUT 	 /restaurante/{id}/ativo
	@PutMapping("/{restauranteId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativar(@PathVariable Long restauranteId) {
		cadastroRestauranteService.ativar(restauranteId);
	}
	
	//DELETE /restaurante/{id}/ativo
	@DeleteMapping("/{restauranteId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativar(@PathVariable Long restauranteId) {
		cadastroRestauranteService.inativar(restauranteId);
	}
	
	@PutMapping("/{restauranteId}/abertura")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void abertura(@PathVariable Long restauranteId) {
		cadastroRestauranteService.abrirRestaurante(restauranteId);
	}
	
	@PutMapping("/{restauranteId}/fechamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void fechamento(@PathVariable Long restauranteId) {
		cadastroRestauranteService.fecharRestaurante(restauranteId);
	}	
	
}
