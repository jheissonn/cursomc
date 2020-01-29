package com.jhei.cursomc.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.jhei.cursomc.cursomc.domain.Categoria;
import com.jhei.cursomc.cursomc.dto.CategoriaDTO;
import com.jhei.cursomc.cursomc.repositories.CategoriaRepository;
import com.jhei.cursomc.cursomc.services.exceptions.DataIntegrityException;
import com.jhei.cursomc.cursomc.services.exceptions.ObjectNotFoundException;


@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;
	
	public Categoria find(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return  obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! id:" + id + ", Tipo" + Categoria.class.getName()));
	}
	
	public List<Categoria> find() {
		return repo.findAll();
	}
	
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	public Categoria update(Categoria obj) {
		Categoria newOb = find(obj.getId());
		updateData(newOb, obj);
		return repo.save(newOb);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
	    repo.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
		}
	}
	
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}  
	
	public Categoria fromDto(CategoriaDTO obj) {
		return new Categoria(obj.getId(), obj.getNome());
	}
	private void updateData(Categoria newObj, Categoria obj) {
		newObj.setNome(obj.getNome());
	}
}

