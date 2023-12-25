package com.algaworks.algafood.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.CozinhaDTOAssembler;
import com.algaworks.algafood.api.disassembler.CozinhaInputDisassembler;
import com.algaworks.algafood.api.model.DTO.input.CozinhaDTOinput;
import com.algaworks.algafood.api.model.DTO.output.CozinhaDTO;
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
	
	@Autowired
	private CozinhaDTOAssembler cozinhaDTOAssembler;
	
	@Autowired
	private CozinhaInputDisassembler cozinhaInputDisassembler;
	
	/*
	 * produces define que o método pode retornar apenas resposta em formato determinado (no caso, JSON)
	 *	@GetMapping (produces = MediaType.APPLICATION_JSON_VALUE)
	 */
	@GetMapping
	public List<CozinhaDTO> listar (){
		return cozinhaDTOAssembler.toCollectionDTO(cozinhaRepository.findAll());
	}
	
	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	public CozinhasXmlWrapper listarXml() {
		return new CozinhasXmlWrapper(cozinhaRepository.findAll());
	}
	
	
	
	@GetMapping( "{id}")
	public CozinhaDTO buscar (@PathVariable Long id) {
		Cozinha cozinha = cadastroCozinhaService.buscarOuFalhar(id);
		
		CozinhaDTO cozinhaDTO = cozinhaDTOAssembler.toDTO(cozinha);
		
		return cozinhaDTO;
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public CozinhaDTO adicionar (
			@RequestBody 
			@Valid 
			CozinhaDTOinput cozinhaDtOinput) {
		
		System.out.println("Chegou no método salvar cozinha");
		
		Cozinha cozinha = cozinhaInputDisassembler.deDTOparaCozinha(cozinhaDtOinput);
		
		return cozinhaDTOAssembler.toDTO(cadastroCozinhaService.salvar(cozinha));
	}
	
	@PutMapping("{id}")
	public CozinhaDTO atualizar(
			@PathVariable 
			Long id, 
			@RequestBody 
			@Valid 
			CozinhaDTOinput cozinhaDTOinput) {
		
		Cozinha cozinha = cadastroCozinhaService.buscarOuFalhar(id);
		
		cozinhaInputDisassembler.copiarDeDTOparaModelo(cozinhaDTOinput, cozinha);
		
		return cozinhaDTOAssembler.toDTO(cadastroCozinhaService.salvar(cozinha)); 
	}

	
	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar (@PathVariable Long id) {
		cadastroCozinhaService.excluir(id);
	}
	
	
	
	
	
//	@PutMapping("{id}")
//	public ResponseEntity<Cozinha> atualizar(@PathVariable Long id, @RequestBody Cozinha cozinha) {
//		System.out.println("Chegou no método atualizar");
//		
//		Optional<Cozinha> cozinhaAtual = cozinhaRepository.findById(id);
//		
//		if(cozinhaAtual.isPresent()) {
//			//cozinhaBuscar.setNome(cozinha.getNome());
//			//cozinhaBuscar.setId(id);
//			BeanUtils.copyProperties(cozinha, cozinhaAtual.get(), "id");
//			
//			Cozinha cozinhaSalva = cadastroCozinhaService.salvar(cozinhaAtual.get());
//			
//			return ResponseEntity.ok(cozinhaSalva);
//		}
//		
//		return ResponseEntity.notFound().build();
//	}
	
	/*
	 * refatorando o método deletar para trabalhar corretamente com tratamento de erros da API - Capítulo 8
	 */
//	@DeleteMapping("{id}")
//	public ResponseEntity<Cozinha> deletar (@PathVariable Long id) {
//		System.out.println("Chegou no método deletar");
//		
//		try {
//			cadastroCozinhaService.excluir(id);
//
//			return ResponseEntity.noContent().build();
//			
//		} catch (EntidadeNaoEncontradaException e) {
//
//			return ResponseEntity.notFound().build();
//			
//		} catch (EntidadeEmUsoException e) {
//			return ResponseEntity.status(HttpStatus.CONFLICT).build();
//		}
//	}
	/*
	 * usando ResponseStatusException
	 */
//	@DeleteMapping("{id}")
//	@ResponseStatus(HttpStatus.NO_CONTENT)
//	public void deletar (@PathVariable Long id) {
//		try {
//			cadastroCozinhaService.excluir(id);
//		} catch (EntidadeNaoEncontradaException e) {
//			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
//			//throw new ServerWebInputException(e.getMessage());
//		}
//	}
/*
 * usando @ResponseStatus 	
 */
//	@DeleteMapping("{id}")
//	@ResponseStatus(HttpStatus.NO_CONTENT)
//	public void deletar (@PathVariable Long id) {
//		cadastroCozinhaService.excluir(id);
//	}
	
	
	
	/*
	 * extendendo ResponseStatusException
	 */
//	@DeleteMapping("{id}")
//	public void deletar (@PathVariable Long id) {
//		cadastroCozinhaService.excluir(id);
//	}
	
	//jeito 1 de retornar status especificado
		//@ResponseStatus(value = HttpStatus.ACCEPTED)
	//  JEITO ANTIGO
//		@GetMapping( "{id}")
//		public ResponseEntity<Cozinha> buscar(@PathVariable Long id) {
//			Optional<Cozinha> cozinha = cozinhaRepository.findById(id);
//			
//			if(cozinha.isPresent()) {
//				//return ResponseEntity.status(203).body(cozinha);
//				return ResponseEntity.ok(cozinha.get());
//			}
//			return ResponseEntity.notFound().build();
//		}
}
