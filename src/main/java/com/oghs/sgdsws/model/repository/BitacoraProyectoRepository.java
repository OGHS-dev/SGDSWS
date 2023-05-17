package com.oghs.sgdsws.model.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.oghs.sgdsws.model.entity.BitacoraProyecto;
import com.oghs.sgdsws.model.entity.Proyecto;

/**
 *
 * @author oghs
 */
public interface BitacoraProyectoRepository extends PagingAndSortingRepository<BitacoraProyecto, Long>, CrudRepository<BitacoraProyecto, Long> {
    
    List<BitacoraProyecto> findByProyecto(Proyecto proyecto);

    Page<BitacoraProyecto> findByProyecto(Proyecto proyecto, PageRequest pageRequest);

}
