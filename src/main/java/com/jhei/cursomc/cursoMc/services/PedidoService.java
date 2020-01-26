package com.jhei.cursomc.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhei.cursomc.cursomc.domain.Pedido;
import com.jhei.cursomc.cursomc.repositories.PedidoRepository;
import com.jhei.cursomc.cursomc.services.exceptions.ObjectNotFoundException;


@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;
	
	public Pedido buscar(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return  obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! id:" + id + ", Tipo" + Pedido.class.getName()));
	}
	
	public List<Pedido> buscar() {
		return repo.findAll();
	}
}

