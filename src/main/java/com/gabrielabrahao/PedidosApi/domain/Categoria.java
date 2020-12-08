package com.gabrielabrahao.PedidosApi.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;



import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;

@Entity
public class Categoria extends AbstractEntity<Integer>{
	private static final long serialVersionUID = 1L;
	
	
    @NotNull
	private String nome;
    
    @JsonManagedReference//Proteção para referência cíclica na serialização Json:
    @ManyToMany(mappedBy = "categorias")
    private List<Produto> produtos = new ArrayList<>(); 
		
	
	
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

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	
	

}
