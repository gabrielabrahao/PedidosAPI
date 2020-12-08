package com.gabrielabrahao.PedidosApi.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Cidade extends AbstractEntity<Integer> {
	private static final long serialVersionUID = 1L;

	private String nome;

	@ManyToOne
	@JoinColumn(name = "estado_id")
	private Estado estado;

	public Cidade() {
		// TODO Auto-generated constructor stub
	}

	public Cidade(Integer id, String nome, Estado estado) {
		super();
		this.setId(id);
		this.nome = nome;
		this.estado = estado;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

}
