package com.oghs.sgdsws.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.oghs.sgdsws.model.entity.Incidente;

/**
 *
 * @author oghs
 */
public interface IncidenteRepository extends PagingAndSortingRepository<Incidente, Long>, CrudRepository<Incidente, Long> {
    
    Incidente findByCodigo(String codigo);
    
}
