package com.jhei.cursomc.cursomc.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.jhei.cursomc.cursomc.domain.Cliente;
import com.jhei.cursomc.cursomc.domain.ItemPedido;
import com.jhei.cursomc.cursomc.domain.PagamentoComBoleto;
import com.jhei.cursomc.cursomc.domain.Pedido;
import com.jhei.cursomc.cursomc.domain.enums.EstadoPagamento;
import com.jhei.cursomc.cursomc.repositories.ItemPedidoRepository;
import com.jhei.cursomc.cursomc.repositories.PagamentoRepository;
import com.jhei.cursomc.cursomc.repositories.PedidoRepository;
import com.jhei.cursomc.cursomc.security.UserSS;
import com.jhei.cursomc.cursomc.services.exceptions.AuthorizationException;
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

