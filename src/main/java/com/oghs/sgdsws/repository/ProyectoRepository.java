package com.oghs.sgdsws.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.oghs.sgdsws.model.entity.Proyecto;

/**
 *
 * @author oghs
 */
public interface ProyectoRepository extends PagingAndSortingRepository<Proyecto, Long>, CrudRepository<Proyecto, Long> {
    
}
