package com.gabrielabrahao.PedidosApi.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.gabrielabrahao.PedidosApi.security.UserSS;

public class UserService {

	//Retorna o usuario logado no sistema; 
	public static UserSS authenticated() {
		try {
			return ((UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		} catch (Exception e) {
			return null;
		}
	}
}
