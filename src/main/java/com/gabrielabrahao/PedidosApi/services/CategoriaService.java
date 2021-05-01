package com.gabrielabrahao.PedidosApi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.gabrielabrahao.PedidosApi.domain.Categoria;
import com.gabrielabrahao.PedidosApi.dto.CategoriaDTO;
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
		Categoria objSalvo =  find(obj.getId());
		updateDate(objSalvo,obj);
		return repo.save(objSalvo);
	}

	private void updateDate(Categoria objSalvo, Categoria obj) {
		objSalvo.setNome(obj.getNome());
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

		return repo.findAll(Sort.by(Direction.ASC, "id"));
		
	}
	
	
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy,String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage,Direction.valueOf(direction),orderBy);
		return repo.findAll(pageRequest);
	}

	public Categoria  fromDTO(CategoriaDTO categoriaDTO) {
		return new Categoria(categoriaDTO.getId(),categoriaDTO.getNome());
	}

}
