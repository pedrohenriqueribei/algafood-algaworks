package com.algaworks.algafood.domain.exception.handler;

import java.time.LocalDateTime;

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
	
	private LocalDateTime dataHora;
}
