package com.algaworks.algafood.api.model.DTO.output;

import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UsuarioDTO {

	private Long id;
	private String nome;
	private String email;
	private OffsetDateTime dataCadastro;
}
