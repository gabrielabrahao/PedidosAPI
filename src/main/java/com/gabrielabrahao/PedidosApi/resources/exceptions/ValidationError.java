package com.gabrielabrahao.PedidosApi.resources.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError{
	private static final long serialVersionUID = 1L;

	private List<FieldMessage> list = new ArrayList<>();
	
	
	public ValidationError(Integer status, String msg, Long timeStamp) {
		super(status, msg, timeStamp);
		// TODO Auto-generated constructor stub
	}


	public List<FieldMessage> getErrors() {
		return list;
	}


	public void addErro(String filedname, String mensagem) {
		list.add(new FieldMessage(filedname,mensagem));
	}
	
	
	

}
