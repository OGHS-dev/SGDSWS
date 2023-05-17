package com.oghs.sgdsws.model.service;

import java.util.List;

import com.oghs.sgdsws.model.entity.Proyecto;
import com.oghs.sgdsws.model.entity.Usuario;
import com.oghs.sgdsws.util.Paginado;

/**
 *
 * @author oghs
 */
public interface UsuarioService {
    
    public List<Usuario> obtenerUsuarios();

    public Paginado<Usuario> obtenerUsuariosPaginado(int numeroPagina, int tamano);

    public void guardarUsuario(Usuario usuario);

    public Usuario buscarUsuario(Usuario usuario);
    
    public void eliminarUsuario(Usuario usuario);

    public List<Usuario> obtenerUsuariosPorProyecto(Proyecto proyecto);

}
