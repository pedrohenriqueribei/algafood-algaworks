package com.algaworks.algafood.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import com.algaworks.algafood.wrapper.CozinhasXmlWrapper;

@RestController
@RequestMapping("cozinhas")
public class CozinhaController {
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;
	
	/*
	 * produces define que o método pode retornar apenas resposta em formato determinado (no caso, JSON)
	 *	@GetMapping (produces = MediaType.APPLICATION_JSON_VALUE)
	 */
	@GetMapping
	public List<Cozinha> listar (){
		return cozinhaRepository.todas();
	}
	
	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	public CozinhasXmlWrapper listarXml() {
		return new CozinhasXmlWrapper(cozinhaRepository.todas());
	}
	
	//jeito 1 de retornar status especificado
	//@ResponseStatus(value = HttpStatus.ACCEPTED)
	@GetMapping( "{id}")
	public ResponseEntity<Cozinha> buscar(@PathVariable Long id) {
		Cozinha cozinha = cozinhaRepository.buscar(id);
		
		if(cozinha != null) {
			//return ResponseEntity.status(203).body(cozinha);
			return ResponseEntity.ok(cozinha);
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Cozinha> adicionar (@RequestBody Cozinha cozinha) {
		System.out.println("Chegou no método salvar");
		
		Cozinha cozinhaSalvada = cadastroCozinhaService.salvar(cozinha);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(cozinhaSalvada);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<Cozinha> atualizar(@PathVariable Long id, @RequestBody Cozinha cozinha) {
		System.out.println("Chegou no método atualizar");
		
		Cozinha cozinhaAtual = cozinhaRepository.buscar(id);
		
		if(cozinha != null) {
			//cozinhaBuscar.setNome(cozinha.getNome());
			//cozinhaBuscar.setId(id);
			BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
			
			cozinhaAtual = cadastroCozinhaService.salvar(cozinhaAtual);
			
			return ResponseEntity.ok(cozinhaAtual);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Cozinha> deletar (@PathVariable Long id) {
		System.out.println("Chegou no método deletar");
		
		try {
			cadastroCozinhaService.excluir(id);

			return ResponseEntity.noContent().build();
			
		} catch (EntidadeNaoEncontradaException e) {

			return ResponseEntity.notFound().build();
			
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}	
}
