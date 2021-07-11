package com.gabrielabrahao.PedidosApi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gabrielabrahao.PedidosApi.domain.Cidade;
import com.gabrielabrahao.PedidosApi.domain.Cliente;
import com.gabrielabrahao.PedidosApi.domain.Endereco;
import com.gabrielabrahao.PedidosApi.domain.enums.Perfil;
import com.gabrielabrahao.PedidosApi.domain.enums.TipoCliente;
import com.gabrielabrahao.PedidosApi.dto.ClienteDTO;
import com.gabrielabrahao.PedidosApi.dto.ClienteNewDTO;
import com.gabrielabrahao.PedidosApi.repositories.ClienteRepository;
import com.gabrielabrahao.PedidosApi.repositories.EnderecoRepository;
import com.gabrielabrahao.PedidosApi.security.UserSS;
import com.gabrielabrahao.PedidosApi.services.exceptions.AuthorizationException;
import com.gabrielabrahao.PedidosApi.services.exceptions.DataIntegrityException;
import com.gabrielabrahao.PedidosApi.services.exceptions.ObjectNotFoundException;


@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private BCryptPasswordEncoder pe;

	public Cliente find(Integer id)  {
		UserSS user = UserService.authenticated();
		if (user==null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}
	
	
	public Cliente update(Cliente obj) {
		Cliente objSalvo =  find(obj.getId());
		updateDate(objSalvo,obj);
		return repo.save(objSalvo);
	}

	

	public void delete(Integer id) {
		find(id);
		//DataIntegrityViolationException
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma Pessoa que possui Pedidos relacionadas");
		}
		
	
	}

	public List<Cliente> findAll() {

		return repo.findAll(Sort.by(Direction.ASC, "id"));
		
	}
	
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy,String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage,Direction.valueOf(direction),orderBy);
		return repo.findAll(pageRequest);
	} 
	
	public Cliente findByEmail(String email) {
		UserSS user = UserService.authenticated();
		if (user == null || !user.hasRole(Perfil.ADMIN) && !email.equals(user.getUsername())) {
			throw new AuthorizationException("Acesso negado");
		}

		Cliente obj = repo.findByEmail(email);
		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + user.getId() + ", Tipo: " + Cliente.class.getName());
		}
		return obj;
	}
	
	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null,null,null);
	}

	public Cliente fromDTO(ClienteNewDTO objDto) {		
		Cliente cli = new Cliente(null,objDto.getNome(), objDto.getEmail(), objDto.getCpfouCnpj(), TipoCliente.toEnum(objDto.getTipo()),pe.encode(objDto.getSenha()) );
		Cidade  cid = new Cidade(objDto.getCidadeID(), null, null);
		Endereco end = new Endereco(objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());
		if (objDto.getTelefone2()!=null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}
		if (objDto.getTelefone3()!=null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}
		return cli;
	}
	
	
	private void updateDate(Cliente objSalvo, Cliente obj) {
		objSalvo.setNome(obj.getNome());
		objSalvo.setEmail(obj.getEmail());
		
	}
	

}
