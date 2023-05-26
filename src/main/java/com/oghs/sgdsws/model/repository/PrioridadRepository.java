package com.oghs.sgdsws.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.oghs.sgdsws.model.entity.Prioridad;

/**
 *
 * @author oghs
 */
public interface PrioridadRepository extends PagingAndSortingRepository<Prioridad, Long>, CrudRepository<Prioridad, Long> {
    
    Prioridad findByCodigo(String codigo);
    
}
