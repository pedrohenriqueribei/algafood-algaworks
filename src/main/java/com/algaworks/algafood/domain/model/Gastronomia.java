package com.algaworks.algafood.domain.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.algaworks.algafood.valid.groups.Groups;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table (name = "tb_cozinha")
@Getter
@Setter
@EqualsAndHashCode(of= "id")
@JsonRootName("cozinha") //mudar o nome do item no json
public class Gastronomia {

	@NotNull(groups = Groups.CozinhaId.class)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	// mudar o nome da propriedade no JSON -> @JsonProperty("descricao")
	// não mandar a propriedade no JSON -> @JsonIgnore
	@NotBlank
	@Column(name = "nome")
	private String nome;
	
	@OneToMany(mappedBy = "cozinha")
	private List<Restaurante>restaurantes = new ArrayList<>();
	
	@Override
	public String toString() {
		return this.id+" - "+this.nome;
	}
}
