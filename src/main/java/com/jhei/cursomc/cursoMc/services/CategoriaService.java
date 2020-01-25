package com.jhei.cursomc.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhei.cursomc.cursomc.domain.Categoria;
import com.jhei.cursomc.cursomc.repositories.CategoriaRepository;
import com.jhei.cursomc.cursomc.services.exceptions.ObjectNotFoundException;


@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;
	
	public Categoria buscar(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return  obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! id:" + id + ", Tipo" + Categoria.class.getName()));
	}
	
	public List<Categoria> buscar() {
		return repo.findAll();
	}
}

