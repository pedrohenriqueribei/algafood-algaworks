package com.algaworks.algafood.domain.exception.handler;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;

/*
 * @ControllerAdvice
 * 
 * 8.13. Tratando exceções globais com @ExceptionHandler e @ControllerAdvice
 * Ponto central. Todas as exceptions serão tratadas por aqui
 * Este componente recebe todos os exceptions handler e as excessoes de todos os controladores da API serão tratados por aqui
 * 
 * ResponseEntityExceptionHandler
 * 
 * extender ResponseEntityExceptionHandler serve para tratar exceptions globais do SpringMVC
 */
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> tratarEntidadeNaoEncontrada (EntidadeNaoEncontradaException ex, WebRequest request) {
		
		HttpStatus httpStatus = HttpStatus.NOT_FOUND;
		ProblemType problemType = ProblemType.ENTIDADE_NAO_ENCONTRADA;
		String detail = ex.getMessage();
		
		Problema problema = createProblemaBuilder(httpStatus, problemType, detail)				
				.dataHora(LocalDateTime.now())
				.build();
		
		return handleExceptionInternal(ex, problema, new HttpHeaders(), httpStatus, request);
		
	}
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> tratarNegocioException (NegocioException ex, WebRequest request) {
		
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		String detail = ex.getMessage();
		ProblemType problemType = ProblemType.ERRO_NEGOCIO;
		
		Problema problema = createProblemaBuilder(httpStatus, problemType, detail)
				.dataHora(LocalDateTime.now())
				.build();
		
		return handleExceptionInternal(ex, problema, new HttpHeaders(), httpStatus, request);
		
	}
	
	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> tratarEntidadeEmUsoException(EntidadeEmUsoException ex, WebRequest request){
		
		HttpStatus httpStatus = HttpStatus.CONFLICT;
		ProblemType problemType = ProblemType.ENTIDADE_EM_USO;
		String detail = ex.getMessage();
		
		Problema problema = createProblemaBuilder(httpStatus, problemType, detail)
				.dataHora(LocalDateTime.now())
				.build();
		
		return handleExceptionInternal(ex, problema, new HttpHeaders(), httpStatus, request);
		
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		if(body == null) {
			body = Problema.builder()
					.dataHora(LocalDateTime.now())
					.title(status.getReasonPhrase())
					.status(status.value())
					.build();			
		}else if (body instanceof String) {
			body = Problema.builder()
					.dataHora(LocalDateTime.now())
					.title((String)body)
					.status(status.value())
					.build();			
		}
		
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
	
	private Problema.ProblemaBuilder createProblemaBuilder(HttpStatus status, ProblemType problemType, String detail) {
		
		return Problema
				.builder()
				.status(status.value())
				.type(problemType.getUri())
				.title(problemType.getTitle())
				.details(detail);
	}

	/*
	 * 8.20. Customizando exception handlers de ResponseEntityExceptionHandler
	 * Sobrescrevendo o método handleHttpMessageNotReadable
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
	
		Throwable rootCause = ExceptionUtils.getRootCause(ex);
		
		if(rootCause instanceof InvalidFormatException) {
			return handleInvalidFormatException((InvalidFormatException)rootCause, headers, status, request);
		} else if(rootCause instanceof PropertyBindingException) {
			return handlePropertyBindingException((PropertyBindingException)rootCause, headers, status, request);
		}

		ProblemType problemType = ProblemType.MENSAGEM_NAO_COMPREENSIVEL;
		String detail = "O corpo da requisição esstá inválida. Verifique erro de sintaxe.";
		
		Problema problema = createProblemaBuilder(status, problemType, detail)				
				.dataHora(LocalDateTime.now())
				.build();
		
		return handleExceptionInternal(ex, problema, headers, status, request);
	
	}

	private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		String atr = ex.getPath().stream()
				.map(ref -> ref.getFieldName())
				.collect(Collectors.joining("-"));
		
		ProblemType problemType = ProblemType.MENSAGEM_NAO_COMPREENSIVEL;
		String detail = String.format("O campo '%s' não é válido!!", atr);
		Problema problema = createProblemaBuilder(status, problemType, detail)				
				.dataHora(LocalDateTime.now())
				.build();
		return handleExceptionInternal(ex, problema, headers, status, request);
	}

	private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		ex.getPath().forEach(var -> System.out.println(var.getFieldName()));
		
		String path = ex.getPath().stream()
				.map(ref -> ref.getFieldName())
				.collect(Collectors.joining("."));
		
		ProblemType problemType = ProblemType.MENSAGEM_NAO_COMPREENSIVEL;
		String detail = String.format("A propriedade '%s' recebeu um valor '%s' que é de um tipo inválido. "
				+ "Corrija para um valor compatível com o tipo %s", path, ex.getValue(), ex.getTargetType().getSimpleName());
		Problema problema = createProblemaBuilder(status, problemType, detail)
				.dataHora(LocalDateTime.now())
				.build();

		
		return handleExceptionInternal(ex, problema, headers, status, request);
	}
	
}
