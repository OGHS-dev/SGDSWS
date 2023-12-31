package com.oghs.sgdsws.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.oghs.sgdsws.model.Estatus;
import com.oghs.sgdsws.model.entity.Prioridad;

/**
 *
 * @author oghs
 */
public interface PrioridadRepository extends PagingAndSortingRepository<Prioridad, Long>, CrudRepository<Prioridad, Long> {
    
    List<Prioridad> findAllByEstatusOrderByCodigoAsc(Estatus estatus);

    Prioridad findByCodigo(String codigo);
    
}
