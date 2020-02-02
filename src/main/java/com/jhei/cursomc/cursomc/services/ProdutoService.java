package com.jhei.cursomc.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.jhei.cursomc.cursomc.domain.Categoria;
import com.jhei.cursomc.cursomc.domain.Produto;
import com.jhei.cursomc.cursomc.repositories.CategoriaRepository;
import com.jhei.cursomc.cursomc.repositories.ProdutoRepository;
import com.jhei.cursomc.cursomc.services.exceptions.ObjectNotFoundException;


@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository repo;
	
	@Autowired
	private CategoriaRepository repoCat;
	
	public Produto buscar(Integer id) {
		Optional<Produto> obj = repo.findById(id);
		return  obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! id:" + id + ", Tipo" + Produto.class.getName()));
	}
	
	public List<Produto> buscar() {
		return repo.findAll();
	}

	public Page<Produto> search(String nome, List<Integer>ids, Integer page, Integer linesPerPage, String orderBy, String direction) {

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = repoCat.findAllById(ids);
		return repo.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
	} 
}

