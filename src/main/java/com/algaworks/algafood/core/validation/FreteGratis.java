package com.algaworks.algafood.core.validation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

//ElementType.TYPE não pode ser utilizada em um propriedade, apenas em um tipo: classe, interface...
@Target({ ElementType.TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = { FreteGratisIncluiDescricaoValidator.class })
public @interface FreteGratis {
	
	String message() default "Descrição Obrigatória para frete grátis inválida!!";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
	
	String valorField();
	
	String descricaoField();
	
	String descricaoObrigatoria ();

	
}
