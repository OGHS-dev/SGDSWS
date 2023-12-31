package com.oghs.sgdsws.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.oghs.sgdsws.model.entity.Proyecto;
import com.oghs.sgdsws.model.entity.Usuario;
import com.oghs.sgdsws.model.entity.UsuarioProyecto;
import com.oghs.sgdsws.model.entity.UsuarioProyectoId;

/**
 *
 * @author oghs
 */
public interface UsuarioProyectoRepository extends CrudRepository<UsuarioProyecto, UsuarioProyectoId> {
    
    List<UsuarioProyecto> findByProyecto(Proyecto proyecto);

    List<UsuarioProyecto> findByUsuario(Usuario usuario);
    
}
