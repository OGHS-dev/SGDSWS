package com.oghs.sgdsws.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.oghs.sgdsws.model.Estatus;
import com.oghs.sgdsws.model.entity.Usuario;

/**
 *
 * @author oghs
 */
public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Long>, CrudRepository<Usuario, Long> {

    List<Usuario> findAllByEstatusOrderByNombreUsuarioAsc(Estatus estatus);
    
    Usuario findByNombreUsuario(String nombreUsuario);
    
}
