package com.algaworks.algafood.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.algaworks.algafood.valid.groups.Groups.EstadoId;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity(name = "Estado")
@Table(name = "tb_estado")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Estado {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	@NotNull(groups = EstadoId.class)
	private Long id;
	
	@NotBlank
	private String nome;
}
