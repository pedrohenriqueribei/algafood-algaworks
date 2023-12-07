package com.algaworks.algafood.domain.exception.handler;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;

@JsonInclude(Include.NON_NULL)
@Getter
@Builder
public class Problema {

	/*
	 * 8.18. Padronizando o formato de problemas no corpo de respostas com a RFC 7807
	 */
	private Integer status;
	private String type;
	private String title;
	private String details;
	private String instance;
	
	
	//8.28. Estendendo o formato do problema para adicionar novas propriedades
	private String userMessage;
	private OffsetDateTime dataHora;
	
	//9.4. Estendendo o Problem Details para adicionar as propriedades com constraints violadas
	private List<Object> listaErros;
	
	@Getter
	@Builder
	public static class Object {
		private String name;
		private String userMessage;
	}
}
