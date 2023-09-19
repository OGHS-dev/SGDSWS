package com.oghs.sgdsws.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.oghs.sgdsws.model.entity.Hallazgo;

/**
 *
 * @author oghs
 */
public interface HallazgoRepository extends PagingAndSortingRepository<Hallazgo, Long>, CrudRepository<Hallazgo, Long> {
    
    Hallazgo findByCodigo(String codigo);
    
}
