package com.oghs.sgdsws.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.oghs.sgdsws.model.Estatus;
import com.oghs.sgdsws.model.entity.NivelRiesgo;

/**
 *
 * @author oghs
 */
public interface NivelRiesgoRepository extends PagingAndSortingRepository<NivelRiesgo, Long>, CrudRepository<NivelRiesgo, Long> {
    
    List<NivelRiesgo> findAllByEstatusOrderByCodigoAsc(Estatus estatus);
    
    NivelRiesgo findByCodigo(String codigo);
    
}
