package com.algaworks.algafood.domain.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table (name = "tb_grupo")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Grupo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	
	@Column(nullable = false)
	private String nome;
	
	@ManyToMany
	@JoinTable (name = "tb_grupo_permissao",
		joinColumns = @JoinColumn(name = "grupo_id"),
		inverseJoinColumns = @JoinColumn(name="permissao_id"))
	private List<Permissao> permissoes = new ArrayList<>();
	
	
	public boolean associarPermissao(Permissao permissao) {
		return getPermissoes().add(permissao);
	}
	
	public boolean desassociarPermissao(Permissao permissao) {
		return getPermissoes().remove(permissao);
	}
}
