package com.jhei.cursomc.cursomc.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhei.cursomc.cursomc.domain.ItemPedido;
import com.jhei.cursomc.cursomc.domain.PagamentoComBoleto;
import com.jhei.cursomc.cursomc.domain.Pedido;
import com.jhei.cursomc.cursomc.domain.enums.EstadoPagamento;
import com.jhei.cursomc.cursomc.repositories.ItemPedidoRepository;
import com.jhei.cursomc.cursomc.repositories.PagamentoRepository;
import com.jhei.cursomc.cursomc.repositories.PedidoRepository;
import com.jhei.cursomc.cursomc.services.exceptions.ObjectNotFoundException;


@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;
	@Autowired
	private BoletoService boletoService;
	@Autowired 
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoService serviceProtudo;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private ClienteService clienteService;
	@Autowired
	private EmailService emailService;
	
	public Pedido buscar(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return  obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! id:" + id + ", Tipo" + Pedido.class.getName()));
	}
	
	public List<Pedido> buscar() {
		return repo.findAll();
	}

	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(clienteService.find(obj.getCliente().getId()));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.PreencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		for(ItemPedido ip: obj.getItens()) {
			ip.setDesconto(0.0);		
			ip.setProduto(serviceProtudo.buscar(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItens());
		emailService.sendOrderConfirmationEmail(obj);
		return obj;
	}
}

