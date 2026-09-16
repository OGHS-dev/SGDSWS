package com.oghs.sgdsws.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oghs.sgdsws.model.Estatus;
import com.oghs.sgdsws.model.entity.Usuario;

/**
 *
 * @author oghs
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    List<Usuario> findAllByEstatusOrderByNombreUsuarioAsc(Estatus estatus);
    
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
    
}
