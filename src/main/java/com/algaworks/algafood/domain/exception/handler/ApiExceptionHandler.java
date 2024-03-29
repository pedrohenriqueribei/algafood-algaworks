package com.algaworks.algafood.domain.exception.handler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.ValidacaoException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
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
	
	public static final String MSG_ERRO_USUARIO_FINAL = "Ocorreu um erro interno inesperado no sistema. Tente novamene em alguns instantes. Se o problema persistir, entre em contato com o administrador";
	
	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> tratarEntidadeNaoEncontrada (EntidadeNaoEncontradaException ex, WebRequest request) {
		
		HttpStatus httpStatus = HttpStatus.NOT_FOUND;
		ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
		String detail = ex.getMessage();
		
		Problema problema = createProblemaBuilder(httpStatus, problemType, detail)				
				.dataHora(OffsetDateTime.now())
				.build();
		
		return handleExceptionInternal(ex, problema, new HttpHeaders(), httpStatus, request);
		
	}
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> tratarExceptionDeNegocio (NegocioException ex, WebRequest request) {
		
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		String detail = ex.getMessage();
		ProblemType problemType = ProblemType.ERRO_NEGOCIO;
		
		Problema problema = createProblemaBuilder(httpStatus, problemType, detail)
				.dataHora(OffsetDateTime.now())
				.build();
		
		return handleExceptionInternal(ex, problema, new HttpHeaders(), httpStatus, request);
		
	}
	
	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> tratarEntidadeEmUso(EntidadeEmUsoException ex, WebRequest request){
		
		HttpStatus httpStatus = HttpStatus.CONFLICT;
		ProblemType problemType = ProblemType.ENTIDADE_EM_USO;
		String detail = ex.getMessage();
		
		Problema problema = createProblemaBuilder(httpStatus, problemType, detail)
				.dataHora(OffsetDateTime.now())
				.userMessage(detail)
				.build();
		
		return handleExceptionInternal(ex, problema, new HttpHeaders(), httpStatus, request);
		
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleErroSistema(Exception ex, WebRequest webRequest) {
		
		HttpStatus httpStatus = HttpStatus.SERVICE_UNAVAILABLE;
		ProblemType problemType = ProblemType.ERRO_SISTEMA;
		String detail = MSG_ERRO_USUARIO_FINAL;
		
		Problema problema = createProblemaBuilder(httpStatus, problemType, detail)
				.dataHora(OffsetDateTime.now())
				.build();
		return handleExceptionInternal(ex, problema, new HttpHeaders(), httpStatus, webRequest);
	}
	
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
				HttpHeaders headers, HttpStatus status, WebRequest webRequest) {
		
		HttpStatus httpStatus = HttpStatus.NOT_ACCEPTABLE;
		
		
		return handleValidationInternal(ex, ex.getBindingResult(), headers, httpStatus, webRequest);
	}
	
	@ExceptionHandler(ValidacaoException.class)
	public ResponseEntity<Object> handleValidacaoProgramatica(ValidacaoException ex, WebRequest webRequest){
		
		HttpStatus httpStatus = HttpStatus.FORBIDDEN;
		
		return handleValidationInternal(ex, ex.getBindingResult(), new HttpHeaders(), httpStatus, webRequest);
	}
	
	private ResponseEntity<Object> handleValidationInternal (Exception ex, BindingResult bindingResult, HttpHeaders headers, HttpStatus status, WebRequest webRequest) {
		
		ProblemType problemType = ProblemType.DADOS_INVALIDOS;
		String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";
		
		List<Problema.Object> problemaObjects = bindingResult.getAllErrors().stream()
				.map(objectError -> {
					String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
					
					String name = objectError.getObjectName();
					
					if(objectError instanceof FieldError) {
						name = ((FieldError) objectError).getField();
					}
					
					return Problema.Object.builder()
						.name(name)
						.userMessage(message)
						.build();
				})
				.collect(Collectors.toList());
		
		Problema problema = createProblemaBuilder(status, problemType, detail)
			.dataHora(OffsetDateTime.now())
			.listaErros(problemaObjects)
			.userMessage("Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.")
			.build();
		
		return handleExceptionInternal(ex, problema, headers, status, webRequest);
	}
	
	
	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders httpHeaders, HttpStatus httpStatus, WebRequest webRequest) {
		
		if(ex instanceof MethodArgumentTypeMismatchException) {
			return handleMethodArgumentTypeMismatch((MethodArgumentTypeMismatchException) ex, httpHeaders, httpStatus, webRequest);
		}
		
		return super.handleTypeMismatch(ex, httpHeaders, httpStatus, webRequest);
		
	}
	
	private ResponseEntity<Object> handleMethodArgumentTypeMismatch (MethodArgumentTypeMismatchException ex, HttpHeaders httpHeaders, HttpStatus httpStatus, WebRequest webRequest) {
		
		ProblemType problemType = ProblemType.PARAMETRO_INVALIDO;
		String detail = String.format("O parâmetro de URL '%s' recebeu o valor '%s', "
	            + "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
	            ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

	    Problema problem = createProblemaBuilder(httpStatus, problemType, detail)
	    		.dataHora(OffsetDateTime.now())
	    		.build();

	    return handleExceptionInternal(ex, problem, httpHeaders, httpStatus, webRequest);
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		if(body == null) {
			body = Problema.builder()
					.dataHora(OffsetDateTime.now())
					.title(status.getReasonPhrase())
					.status(status.value())
					.build();			
		}else if (body instanceof String) {
			body = Problema.builder()
					.dataHora(OffsetDateTime.now())
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
				.dataHora(OffsetDateTime.now())
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
			return handleInvalidFormat((InvalidFormatException)rootCause, headers, status, request);
		} else if(rootCause instanceof PropertyBindingException) {
			return handlePropertyBinding((PropertyBindingException)rootCause, headers, status, request);
		} 

		ProblemType problemType = ProblemType.MENSAGEM_NAO_COMPREENSIVEL;
		String detail = "O corpo da requisição esstá inválida. Verifique erro de sintaxe.";
		
		Problema problema = createProblemaBuilder(status, problemType, detail)				
				.dataHora(OffsetDateTime.now())
				.build();
		
		return handleExceptionInternal(ex, problema, headers, status, request);
	
	}
	
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException (NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
		String detail = String.format("O recurso '%s', que você tentou acessar é inexistente!", ex.getRequestURL());
		HttpStatus statusHttp = HttpStatus.EXPECTATION_FAILED;
		
		Problema problema = createProblemaBuilder(statusHttp, problemType, detail)
				.dataHora(OffsetDateTime.now())
				.build();
		
		
		return handleExceptionInternal(ex, problema, headers, statusHttp, request);
	}

	private ResponseEntity<Object> handlePropertyBinding (PropertyBindingException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		String atr = joinPath(ex.getPath());
		
		ProblemType problemType = ProblemType.MENSAGEM_NAO_COMPREENSIVEL;
		String detail = String.format("O campo '%s' não é válido!!", atr);
		Problema problema = createProblemaBuilder(status, problemType, detail)				
				.dataHora(OffsetDateTime.now())
				.userMessage(MSG_ERRO_USUARIO_FINAL)
				.build();
		return handleExceptionInternal(ex, problema, headers, status, request);
	}

	private ResponseEntity<Object> handleInvalidFormat (InvalidFormatException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		ex.getPath().forEach(var -> System.out.println(var.getFieldName()));
		
		String path = joinPath(ex.getPath());
		
		ProblemType problemType = ProblemType.MENSAGEM_NAO_COMPREENSIVEL;
		String detail = String.format("A propriedade '%s' recebeu um valor '%s' que é de um tipo inválido. "
				+ "Corrija para um valor compatível com o tipo %s", path, ex.getValue(), ex.getTargetType().getSimpleName());
		
		Problema problema = createProblemaBuilder(status, problemType, detail)
				.dataHora(OffsetDateTime.now())
				.userMessage(MSG_ERRO_USUARIO_FINAL)
				.build();

		
		return handleExceptionInternal(ex, problema, headers, status, request);
	}
	
	private String joinPath(List<Reference> references) {
	    return references.stream()
	        .map(ref -> ref.getFieldName())
	        .collect(Collectors.joining("."));
	} 
	
	

	
	
}
