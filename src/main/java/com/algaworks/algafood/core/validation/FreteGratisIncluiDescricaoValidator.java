package com.algaworks.algafood.core.validation;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;

import org.springframework.beans.BeanUtils;

public class FreteGratisIncluiDescricaoValidator implements ConstraintValidator<FreteGratis, Object> {
	
	private String valorField;
	private String descricaoField;
	private String descricaoObrigatoria;

	@Override
	public void initialize(FreteGratis constraintAnnotation) {
		this.valorField = constraintAnnotation.valorField();
		this.descricaoField = constraintAnnotation.descricaoField();
		this.descricaoObrigatoria = constraintAnnotation.descricaoObrigatoria();
	}
	
	@Override
	public boolean isValid(Object objetoValidacao, ConstraintValidatorContext context) {
		boolean valido = true;
		
		System.out.println("Valor = "+ valorField);
		
		try {
			BigDecimal valor = (BigDecimal) BeanUtils.getPropertyDescriptor(objetoValidacao.getClass(), valorField).getReadMethod().invoke(objetoValidacao);
			
			String descricao = (String) BeanUtils.getPropertyDescriptor(objetoValidacao.getClass(), descricaoField).getReadMethod().invoke(objetoValidacao);
			
			if (valor != null && BigDecimal.ZERO.compareTo(valor) == 0 && descricao != null) {
				valido = descricao.toLowerCase().contains(descricaoObrigatoria.toLowerCase());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ValidationException(e);
		} 
		
		return valido;
	}

}
