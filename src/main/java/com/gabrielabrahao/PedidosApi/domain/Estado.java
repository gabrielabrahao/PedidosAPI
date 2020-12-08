package com.gabrielabrahao.PedidosApi.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import javax.persistence.OneToMany;

@Entity
public class Estado extends AbstractEntity<Integer> {
	private static final long serialVersionUID = 1L;

	private String nome;

	@OneToMany(mappedBy = "estado")
	private List<Cidade> cidades = new ArrayList<>();

	public Estado() {
		// TODO Auto-generated constructor stub
	}

	public Estado(Integer id, String nome) {
		super();
		this.setId(id);
		this.nome = nome;

	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

}
