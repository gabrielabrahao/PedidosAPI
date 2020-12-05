package com.gabrielabrahao.PedidosApi.domain;

import javax.persistence.Entity;

import com.sun.istack.NotNull;

@Entity
public class Categoria extends AbstractEntity<Integer>{
	private static final long serialVersionUID = 1L;
	
	
    @NotNull
	private String nome;
	
	public Categoria() {
	
	}

	public Categoria(Integer id, String nome) {
		super();
	    Categoria.this.setId(id);
		this.nome = nome;
	}


	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	
	

}
