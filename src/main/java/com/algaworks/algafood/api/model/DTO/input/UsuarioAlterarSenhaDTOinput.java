package com.algaworks.algafood.api.model.DTO.input;

import lombok.Data;

@Data
public class UsuarioAlterarSenhaDTOinput {

	private String senhaAtual;
	private String novaSenha;
	
	public Boolean senhaIguais(String senhaAtual, String novaSenha) {
		return senhaAtual.equals(novaSenha);
	}
}
