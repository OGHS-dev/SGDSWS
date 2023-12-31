package com.oghs.sgdsws.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.oghs.sgdsws.model.Estatus;
import com.oghs.sgdsws.model.entity.Impacto;

/**
 *
 * @author oghs
 */
public interface ImpactoRepository extends PagingAndSortingRepository<Impacto, Long>, CrudRepository<Impacto, Long> {

    List<Impacto> findAllByEstatusOrderByCodigoAsc(Estatus estatus);
    
    Impacto findByCodigo(String codigo);
    
}
