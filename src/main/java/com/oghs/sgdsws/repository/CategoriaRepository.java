package com.oghs.sgdsws.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.oghs.sgdsws.model.Estatus;
import com.oghs.sgdsws.model.entity.Categoria;

/**
 *
 * @author oghs
 */
public interface CategoriaRepository extends PagingAndSortingRepository<Categoria, Long>, CrudRepository<Categoria, Long> {
    
    List<Categoria> findAllByEstatusOrderByCodigoAsc(Estatus estatus);
    
    Categoria findByCodigo(String codigo);
    
}
