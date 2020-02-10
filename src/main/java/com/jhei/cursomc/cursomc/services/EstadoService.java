package com.jhei.cursomc.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhei.cursomc.cursomc.domain.Estado;
import com.jhei.cursomc.cursomc.repositories.EstadoRepository;


@Service
public class EstadoService {
	
	@Autowired
	private EstadoRepository repo;
	
	public List<Estado> findAll() {
		return repo.findAllByOrderByNome();
	}

}

