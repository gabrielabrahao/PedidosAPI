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

import com.gabrielabrahao.PedidosApi.domain.Cliente;
import com.gabrielabrahao.PedidosApi.dto.ClienteDTO;
import com.gabrielabrahao.PedidosApi.repositories.ClienteRepository;
import com.gabrielabrahao.PedidosApi.services.exceptions.DataIntegrityException;
import com.gabrielabrahao.PedidosApi.services.exceptions.ObjectNotFoundException;


@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;

	public Cliente find(Integer id)  {
		
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	public Cliente update(Cliente obj) {
		Cliente objSalvo =  find(obj.getId());
		updateDate(objSalvo,obj);
		return repo.save(objSalvo);
	}

	private void updateDate(Cliente objSalvo, Cliente obj) {
		objSalvo.setNome(obj.getNome());
		objSalvo.setEmail(obj.getEmail());
		
	}

	public void delete(Integer id) {
		find(id);
		//DataIntegrityViolationException
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma Pessoa que possui entidades relacionadas");
		}
		
	
	}

	public List<Cliente> findAll() {

		return repo.findAll(Sort.by(Direction.ASC, "id"));
		
	}
	
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy,String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage,Direction.valueOf(direction),orderBy);
		return repo.findAll(pageRequest);
	}

	public Cliente  fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);		
	}

}
