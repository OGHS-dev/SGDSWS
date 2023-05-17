package com.oghs.sgdsws.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.oghs.sgdsws.model.entity.Impacto;

/**
 *
 * @author oghs
 */
public interface ImpactoRepository extends PagingAndSortingRepository<Impacto, Long>, CrudRepository<Impacto, Long> {
    
}
