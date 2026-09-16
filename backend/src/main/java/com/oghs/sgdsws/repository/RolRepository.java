package com.oghs.sgdsws.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oghs.sgdsws.model.Estatus;
import com.oghs.sgdsws.model.entity.Rol;

/**
 *
 * @author oghs
 */
public interface RolRepository extends JpaRepository<Rol, Long> {
    
    List<Rol> findAllByEstatusOrderByDescripcionAsc(Estatus estatus);
    
}
