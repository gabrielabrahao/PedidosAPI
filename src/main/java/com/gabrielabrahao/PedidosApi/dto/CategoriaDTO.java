package com.gabrielabrahao.PedidosApi.dto;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.gabrielabrahao.PedidosApi.domain.AbstractEntity;
import com.gabrielabrahao.PedidosApi.domain.Categoria;


public class CategoriaDTO extends AbstractEntity<Integer>{
	private static final long serialVersionUID = 1L;
	
	@NotEmpty
	@Length(min = 5, max = 80, message = "O tamanho deve ser entre 5 e 80 caracteres")
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
