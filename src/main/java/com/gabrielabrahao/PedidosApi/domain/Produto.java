package com.gabrielabrahao.PedidosApi.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.ManyToAny;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Produto extends AbstractEntity<Integer> {
	private static final long serialVersionUID = 1L;

	private String nome;
	private Double preco;

	@JsonBackReference//Proteção para referência cíclica na serialização Json:
	@ManyToMany
	@JoinTable(name="Produto_Categoria",
	joinColumns = @JoinColumn(name="produto_id"),
	inverseJoinColumns = @JoinColumn(name="categoria_id")			)
	private List<Categoria> categorias = new ArrayList<>();

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

	public Produto() {

	}

	public Produto(Integer id, String nome, Double preco) {
		super();
		this.setId(id);
		this.nome = nome;
		this.preco = preco;
	}

}
