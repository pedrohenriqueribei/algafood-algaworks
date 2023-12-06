package com.algaworks.algafood.core.jackson;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.CidadeMixin;
import com.algaworks.algafood.api.model.CozinhaMixin;
import com.algaworks.algafood.api.model.ProdutoMixin;
import com.algaworks.algafood.api.model.RestauranteMixin;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Gastronomia;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.fasterxml.jackson.databind.module.SimpleModule;

@Component
public class JacksonMixinModule extends SimpleModule {
	
	private static final long serialVersionUID = 1L;

	public JacksonMixinModule() {
		setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
		setMixInAnnotation(Cidade.class, CidadeMixin.class);
		setMixInAnnotation(Gastronomia.class, CozinhaMixin.class);
		setMixInAnnotation(Produto.class, ProdutoMixin.class);
	}
}
