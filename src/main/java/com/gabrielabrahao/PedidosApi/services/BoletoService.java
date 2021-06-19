package com.gabrielabrahao.PedidosApi.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.gabrielabrahao.PedidosApi.domain.PagamentoComBoleto;

@Service
public class BoletoService {
 
	public void preencherPagamentoComBoleto(PagamentoComBoleto pagto, Date istanteDoPedido) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(istanteDoPedido);
		cal.add(Calendar.DAY_OF_MONTH, 7);
		pagto.setDataVencimento(cal.getTime());
	}
}
