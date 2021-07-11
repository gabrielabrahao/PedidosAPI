package com.gabrielabrahao.PedidosApi.services;

import java.util.Date;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gabrielabrahao.PedidosApi.domain.Cliente;
import com.gabrielabrahao.PedidosApi.domain.ItemPedido;
import com.gabrielabrahao.PedidosApi.domain.PagamentoComBoleto;
import com.gabrielabrahao.PedidosApi.domain.Pedido;
import com.gabrielabrahao.PedidosApi.domain.Produto;
import com.gabrielabrahao.PedidosApi.domain.enums.EstadoPagamento;
import com.gabrielabrahao.PedidosApi.repositories.ItemPedidoRepository;
import com.gabrielabrahao.PedidosApi.repositories.PagamentoRepository;
import com.gabrielabrahao.PedidosApi.repositories.PedidoRepository;
import com.gabrielabrahao.PedidosApi.security.UserSS;
import com.gabrielabrahao.PedidosApi.services.exceptions.AuthorizationException;
import com.gabrielabrahao.PedidosApi.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;

	@Autowired
	private BoletoService boletoService;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private EmailService emailService;
	
	public Pedido find(Integer id) {

		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}

	@Transactional
	public @Valid Pedido insert(@Valid Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(clienteService.find(obj.getCliente().getId()));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());

		}
		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			Produto produto = produtoService.find(ip.getProduto().getId());
			ip.setProduto(produto);
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItens());
		emailService.sendOrderConfirmationHtmlEmail(obj);
		return obj;
	}
	
	public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Cliente cliente =  clienteService.find(user.getId());		
		return repo.findByCliente(cliente, pageRequest);
	}
	
}
