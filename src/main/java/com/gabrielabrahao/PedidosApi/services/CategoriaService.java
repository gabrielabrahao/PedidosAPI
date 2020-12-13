package com.gabrielabrahao.PedidosApi.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.gabrielabrahao.PedidosApi.domain.Categoria;
import com.gabrielabrahao.PedidosApi.repositories.CategoriaRepository;
import com.gabrielabrahao.PedidosApi.services.exceptions.DataIntegrityException;
import com.gabrielabrahao.PedidosApi.services.exceptions.ObjectNotFoundException;


@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;

	public Categoria find(Integer id)  {
		
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}

	public Categoria insert(Categoria obj) {
       
       return repo.save(obj);
	}

	public Categoria update(Categoria obj) {
		find(obj.getId());
		return repo.save(obj);
	}

	public void delete(Integer id) {
		find(id);
		//DataIntegrityViolationException
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos vinculados");
		}
		
	
	}

	public List<Categoria> findAll() {
		
		List<Categoria> c = repo.findAll();
		Collections.sort();

		return repo.findAll().sort(null);;
		
	}
	
	

}
