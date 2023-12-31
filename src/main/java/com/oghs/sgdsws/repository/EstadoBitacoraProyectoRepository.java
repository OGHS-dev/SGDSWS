package com.oghs.sgdsws.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.oghs.sgdsws.model.Estatus;
import com.oghs.sgdsws.model.entity.EstadoBitacoraProyecto;

/**
 *
 * @author oghs
 */
public interface EstadoBitacoraProyectoRepository extends PagingAndSortingRepository<EstadoBitacoraProyecto, Long>, CrudRepository<EstadoBitacoraProyecto, Long> {
    
    List<EstadoBitacoraProyecto> findAllByEstatusOrderByCodigoAsc(Estatus estatus);
    
    EstadoBitacoraProyecto findByCodigo(String codigo);
    
}
