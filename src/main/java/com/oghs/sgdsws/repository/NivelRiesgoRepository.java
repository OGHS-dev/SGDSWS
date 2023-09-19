package com.oghs.sgdsws.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.oghs.sgdsws.model.entity.NivelRiesgo;

/**
 *
 * @author oghs
 */
public interface NivelRiesgoRepository extends PagingAndSortingRepository<NivelRiesgo, Long>, CrudRepository<NivelRiesgo, Long> {
    
    NivelRiesgo findByCodigo(String codigo);
    
}
