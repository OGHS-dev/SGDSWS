package com.oghs.sgdsws.model.service;

import java.util.List;

import com.oghs.sgdsws.model.entity.Rol;
import com.oghs.sgdsws.util.Paginado;

/**
 *
 * @author oghs
 */
public interface RolService {
   
    public List<Rol> obtenerRoles();

    public Paginado<Rol> obtenerRolesPaginado(int numeroPagina, int tamano);

    public void guardarRol(Rol rol);

    public Rol buscarRol(Rol rol);

    public void eliminarRol(Rol rol);
    
}
