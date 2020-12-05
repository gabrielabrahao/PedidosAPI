package com.gabrielabrahao.PedidosApi.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gabrielabrahao.PedidosApi.domain.Categoria;
import com.gabrielabrahao.PedidosApi.repositories.CategoriaRepository;

@Service
public class CategoriaService {
  
	@Autowired
	private CategoriaRepository repo;
	
	public Categoria find(Integer id) {
		
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElse(null);
	}
	
	
}
