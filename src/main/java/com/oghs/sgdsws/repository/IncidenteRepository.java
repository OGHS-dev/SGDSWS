package com.oghs.sgdsws.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.oghs.sgdsws.model.Estatus;
import com.oghs.sgdsws.model.entity.Incidente;

/**
 *
 * @author oghs
 */
public interface IncidenteRepository extends PagingAndSortingRepository<Incidente, Long>, CrudRepository<Incidente, Long> {
    
    List<Incidente> findAllByEstatusOrderByCodigoAsc(Estatus estatus);
    
    Incidente findByCodigo(String codigo);
    
}
