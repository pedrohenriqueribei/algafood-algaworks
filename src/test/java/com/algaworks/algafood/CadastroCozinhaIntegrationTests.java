package com.algaworks.algafood;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Gastronomia;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

@SuppressWarnings("unused")
@SpringBootTest
class CadastroCozinhaIntegrationTests {
	
	@Autowired
	private CadastroCozinhaService cozinhaService;

	@Test
	public void testarCadastroCozinhaComSucesso() {
		//cenário
		Gastronomia cozinha = new Gastronomia();	
		cozinha.setNome("Chilena");
		
		//ação
		cozinhaService.salvar(cozinha);
		
		//validação
		assertThat(cozinha).isNotNull();
		assertThat(cozinha.getId()).isNotNull();
	}
	
	@Test
	public void testarCadastroCozinhaSemNome() {
		//cenário
		Gastronomia cozinha = new Gastronomia();
		
		
		/*
		 * validação
		 * a ConstraintViolationException é um ValidationException
		 * exception de validação, pois annotamos a Cozinha com a anotação @NotBlank em nome
		 */
		ConstraintViolationException erroEsperado = Assertions.assertThrows(ConstraintViolationException.class, 
			() -> { 
				//ação: ao salvar sem nome espera uma exception de validação
				cozinhaService.salvar(cozinha);	
			});
	}
	
	@Test
	public void testarExcluirCozinhaEmUso() {
		
		//cenário
		
		
		//ação
		
		
		//validação
		EntidadeEmUsoException erroEsperado = Assertions.assertThrows(EntidadeEmUsoException.class, 
				() -> { 
					cozinhaService.excluir(1L); 
			});
		
		assertThat(erroEsperado).isNotNull();
	}
	
	@Test
	public void testarExcluirCozinhaInexistente() {

		//cenário
		
		
		//ação
		
		
		//validação
		CozinhaNaoEncontradaException erroEsperado = Assertions.assertThrows(CozinhaNaoEncontradaException.class, 
			() -> {
				cozinhaService.excluir(1000L);
			});
		
		assertThat(erroEsperado).isNotNull();
	}

}
