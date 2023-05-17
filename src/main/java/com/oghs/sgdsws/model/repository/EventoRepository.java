package com.oghs.sgdsws.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.oghs.sgdsws.model.entity.Evento;

/**
 *
 * @author oghs
 */
public interface EventoRepository extends PagingAndSortingRepository<Evento, Long>, CrudRepository<Evento, Long> {
    
}
