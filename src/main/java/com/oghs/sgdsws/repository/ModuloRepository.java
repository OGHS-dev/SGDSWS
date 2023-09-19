package com.oghs.sgdsws.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.oghs.sgdsws.model.entity.Modulo;

/**
 *
 * @author oghs
 */
public interface ModuloRepository extends PagingAndSortingRepository<Modulo, Long>, CrudRepository<Modulo, Long> {
    
    Modulo findByCodigo(String codigo);
    
}
