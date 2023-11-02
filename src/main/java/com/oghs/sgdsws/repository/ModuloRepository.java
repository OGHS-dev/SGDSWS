package com.oghs.sgdsws.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.oghs.sgdsws.model.Estatus;
import com.oghs.sgdsws.model.entity.Modulo;

/**
 *
 * @author oghs
 */
public interface ModuloRepository extends PagingAndSortingRepository<Modulo, Long>, CrudRepository<Modulo, Long> {
    
    List<Modulo> findAllByEstatusOrderByCodigoAsc(Estatus estatus);
    
    Modulo findByCodigo(String codigo);
    
}
