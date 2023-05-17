package com.oghs.sgdsws.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.oghs.sgdsws.model.entity.EstadoProyecto;

/**
 *
 * @author oghs
 */
public interface EstadoProyectoRepository extends PagingAndSortingRepository<EstadoProyecto, Long>, CrudRepository<EstadoProyecto, Long> {
    
}
