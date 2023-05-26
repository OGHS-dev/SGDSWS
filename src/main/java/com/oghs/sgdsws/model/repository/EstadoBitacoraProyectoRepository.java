package com.oghs.sgdsws.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.oghs.sgdsws.model.entity.EstadoBitacoraProyecto;

/**
 *
 * @author oghs
 */
public interface EstadoBitacoraProyectoRepository extends PagingAndSortingRepository<EstadoBitacoraProyecto, Long>, CrudRepository<EstadoBitacoraProyecto, Long> {
    
    EstadoBitacoraProyecto findByCodigo(String codigo);
    
}
