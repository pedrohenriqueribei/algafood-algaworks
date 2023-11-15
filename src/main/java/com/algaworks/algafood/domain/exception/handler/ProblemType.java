package com.algaworks.algafood.domain.exception.handler;

import lombok.Getter;

@Getter
public enum ProblemType {
	
	RECURSO_NAO_ENCONTRADO ("/recurso-nao-encontrado", "Recurso não encontrado"),
	ERRO_NEGOCIO ("/erro-negocio", "Violação de regra de negócio"),
	ENTIDADE_EM_USO ("/entidade-em-uso", "Entidade em uso"),
	MENSAGEM_NAO_COMPREENSIVEL ("/mensagem-incompreensivel", "Mensagem Incompreensível"),
	PARAMETRO_INVALIDO ("/parametro-invalido", "Parâmetro Inválido"),
	ERRO_SISTEMA ("/erro-de-sistema","Erro de Sistema"),
	DADOS_INVALIDOS ("/dados-invalidos", "Dados Inválidos");
	
	private String title;
	private String uri;
	
	ProblemType (String path, String title) {
		this.uri = "https://algafood.com.br" + path;
		this.title = title;
	}

}
