package com.oghs.sgdsws.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.oghs.sgdsws.model.Estatus;
import com.oghs.sgdsws.model.entity.Rol;

/**
 *
 * @author oghs
 */
public interface RolRepository extends PagingAndSortingRepository<Rol, Long>, CrudRepository<Rol, Long> {
    
    List<Rol> findAllByEstatusOrderByDescripcionAsc(Estatus estatus);
    
}
