package com.gabrielabrahao.PedidosApi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.gabrielabrahao.PedidosApi.domain.Categoria;

@SpringBootApplication//Com o CommandLineRunner posso definir algo a ser rodado na inicialização do Spring
public class PedidosApiApplication implements CommandLineRunner{
	
	

	public static void main(String[] args) {
		SpringApplication.run(PedidosApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		/* Aqui posso definir um metodo a ser rodado na inicialização do Spring
		 * Categoria cat1 = new Categoria(null,"informatica"); Categoria cat2 = new
		 * Categoria(null,"Escritorio");
		 */
		
	}
	
	

}
