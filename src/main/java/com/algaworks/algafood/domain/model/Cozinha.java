package com.algaworks.algafood.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table (name = "tb_cozinhas")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@JsonRootName("cozinha") //mudar o nome do item no json
public class Cozinha {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	// mudar o nome da propriedade no JSON -> @JsonProperty("descricao")
	// não mandar a propriedade no JSON -> @JsonIgnore
	@Column(name = "nome_cozinha")
	private String nome;
	
	@Override
	public String toString() {
		return this.id+" - "+this.nome;
	}
}
