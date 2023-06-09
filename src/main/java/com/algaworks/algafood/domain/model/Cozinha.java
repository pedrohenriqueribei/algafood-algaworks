package com.algaworks.algafood.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table (name = "tb_cozinhas")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Cozinha {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nome_cozinha")
	private String nome;
	
	@Override
	public String toString() {
		return this.id+" - "+this.nome;
	}
}
