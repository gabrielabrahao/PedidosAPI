package com.gabrielabrahao.PedidosApi.dto;

import com.gabrielabrahao.PedidosApi.domain.AbstractEntity;
import com.gabrielabrahao.PedidosApi.domain.Categoria;

public class CategoriaDTO extends AbstractEntity<Integer>{
	private static final long serialVersionUID = 1L;
	
	private String nome;
	
	public CategoriaDTO() {
	
	}
	
	public CategoriaDTO(Categoria obj) {
		this.setId(obj.getId());
		this.nome = obj.getNome();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	

}
