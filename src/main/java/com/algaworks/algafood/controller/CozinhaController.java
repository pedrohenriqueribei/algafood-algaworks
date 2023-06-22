package com.algaworks.algafood.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

@RestController
@RequestMapping("cozinhas")
public class CozinhaController {
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	/*
	 * produces define que o método pode retornar apenas resposta em formato determinado (no caso, JSON)
	 *	@GetMapping (produces = MediaType.APPLICATION_JSON_VALUE)
	 */
	public List<Cozinha> listar (){
		return cozinhaRepository.todas();
	}
	
	@GetMapping( "{id}")
	public Cozinha buscar(@PathVariable Long id) {
		return cozinhaRepository.buscar(id);
	}

}
