package com.oghs.sgdsws.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.oghs.sgdsws.model.entity.Categoria;

/**
 *
 * @author oghs
 */
public interface CategoriaRepository extends PagingAndSortingRepository<Categoria, Long>, CrudRepository<Categoria, Long> {
    
    Categoria findByCodigo(String codigo);
    
}
