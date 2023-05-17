package com.oghs.sgdsws.model.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.oghs.sgdsws.model.entity.Proyecto;
import com.oghs.sgdsws.model.entity.UsuarioProyecto;
import com.oghs.sgdsws.model.entity.UsuarioProyectoId;

/**
 *
 * @author oghs
 */
public interface UsuarioProyectoRepository extends CrudRepository<UsuarioProyecto, UsuarioProyectoId> {
    
    List<UsuarioProyecto> findByProyecto(Proyecto proyecto);
    
}
