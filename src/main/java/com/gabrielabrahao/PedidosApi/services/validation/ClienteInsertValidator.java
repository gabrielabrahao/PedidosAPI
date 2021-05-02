package com.gabrielabrahao.PedidosApi.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.gabrielabrahao.PedidosApi.domain.Cliente;
import com.gabrielabrahao.PedidosApi.domain.enums.TipoCliente;
import com.gabrielabrahao.PedidosApi.dto.ClienteNewDTO;
import com.gabrielabrahao.PedidosApi.repositories.ClienteRepository;
import com.gabrielabrahao.PedidosApi.resources.exceptions.FieldMessage;
import com.gabrielabrahao.PedidosApi.services.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	
	@Autowired
	ClienteRepository repo;
	
	@Override
	public void initialize(ClienteInsert ann) {
	}
	

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		
		List<FieldMessage> list = new ArrayList<>();
         
		// inclua os testes aqui, inserindo erros na lista
		
		if (objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfouCnpj())) {
			list.add(new FieldMessage("cpfouCnpj","CPF inválido"));
		}
		if (objDto.getTipo().equals(TipoCliente.PESOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfouCnpj())) {
			list.add(new FieldMessage("cpfouCnpj","CNPJ inválido"));
		}
		
		Cliente aux = repo.findByEmail(objDto.getEmail());
		if (aux != null) {
			list.add(new FieldMessage("email", "Email já existente"));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}