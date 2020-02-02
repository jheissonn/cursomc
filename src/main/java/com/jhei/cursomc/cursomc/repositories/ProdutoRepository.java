package com.jhei.cursomc.cursomc.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jhei.cursomc.cursomc.domain.Categoria;
import com.jhei.cursomc.cursomc.domain.Produto;


@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
	// @Param("nome") é o parametro que será adicionado na query acima
	
	@Transactional(readOnly=true)
	@Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat WHERE obj.nome LIKE %:nome% AND cat IN :categorias")
	Page<Produto> findDistinctByNomeContainingAndCategoriasIn(@Param("nome") String nome, @Param("categorias") List<Categoria> categorias, Pageable pageRequest);
	// as duas formas estão certas lembrando que @Query é primeiro do que o nome do método. mas os dois fazem a mesma coisa
}
