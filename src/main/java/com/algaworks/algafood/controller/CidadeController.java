package com.algaworks.algafood.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.CidadeDTOAssembler;
import com.algaworks.algafood.api.disassembler.CidadeDisassembler;
import com.algaworks.algafood.api.model.DTO.input.CidadeDTOinput;
import com.algaworks.algafood.api.model.DTO.output.CidadeDTO;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;

@RestController
@RequestMapping("cidades")
public class CidadeController {

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroCidadeService cadastroCidadeService;
	
	@Autowired
	private CidadeDTOAssembler cidadeDTOAssembler;
	
	@Autowired
	private CidadeDisassembler cidadeDisassembler;
	
	@GetMapping
	public List<CidadeDTO> listar (){
		return cidadeDTOAssembler.toCollectDTO(cidadeRepository.findAll());
	}
	
	@GetMapping("{id}")
	public CidadeDTO buscar (@PathVariable Long id) {
		return cidadeDTOAssembler.toDTO(cadastroCidadeService.buscarOufahar(id));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeDTO adicionar (
			@RequestBody 
			@Valid 
			CidadeDTOinput cidadeDTOinput) {
		
		try {
			Cidade cidade = cidadeDisassembler.deDTOparaModelo(cidadeDTOinput);
			return cidadeDTOAssembler.toDTO(cadastroCidadeService.salvar(cidade));
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@PutMapping("{id}")
	public CidadeDTO atualizar (
			@PathVariable 
			Long id, 
			@RequestBody 
			@Valid 
			CidadeDTOinput cidadeDTOinput) {
		
		try {
			Cidade cidade = cadastroCidadeService.buscarOufahar(id);
		
			cidadeDisassembler.copiarParaDominio(cidadeDTOinput, cidade);
			
			return cidadeDTOAssembler.toDTO(cadastroCidadeService.salvar(cidade));			
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar (@PathVariable Long id) {
		System.out.println("Chegou no método deletar cidade");
		
		cadastroCidadeService.excluir(id);
	}
	
	/*
	 * 8.12. Tratando exceções em nível de controlador com @ExceptionHandler
	 */
//	@ExceptionHandler(EntidadeNaoEncontradaException.class)
//	public ResponseEntity<?> tratarEntidadeNaoEncontrada (EntidadeNaoEncontradaException e) {
//		
//		Problema problema = Problema.builder()
//				.dataHora(LocalDateTime.now())
//				.mensagem(e.getMessage())
//				.build();
//		
//		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problema);
//	}
//	
//	@ExceptionHandler(NegocioException.class)
//	public ResponseEntity<?> tratarNegocioException (NegocioException e) {
//		
//		Problema problema = Problema.builder()
//				.dataHora(LocalDateTime.now())
//				.mensagem(e.getMessage())
//				.build();
//		
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problema);
//	}

}
