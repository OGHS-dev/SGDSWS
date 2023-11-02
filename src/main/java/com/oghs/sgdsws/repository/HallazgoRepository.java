package com.oghs.sgdsws.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.oghs.sgdsws.model.Estatus;
import com.oghs.sgdsws.model.entity.Hallazgo;

/**
 *
 * @author oghs
 */
public interface HallazgoRepository extends PagingAndSortingRepository<Hallazgo, Long>, CrudRepository<Hallazgo, Long> {
    
    List<Hallazgo> findAllByEstatusOrderByCodigoAsc(Estatus estatus);
    
    Hallazgo findByCodigo(String codigo);
    
}
