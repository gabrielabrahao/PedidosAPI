package com.gabrielabrahao.PedidosApi.services;

import org.springframework.mail.SimpleMailMessage;

import com.gabrielabrahao.PedidosApi.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);

}
