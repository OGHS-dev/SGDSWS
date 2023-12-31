package com.oghs.sgdsws.service;

import java.util.List;

import com.oghs.sgdsws.model.ProyectoDTO;
import com.oghs.sgdsws.model.entity.Proyecto;
import com.oghs.sgdsws.model.entity.Usuario;
import com.oghs.sgdsws.util.Paginado;

/**
 *
 * @author oghs
 */
public interface ProyectoService {
    
    public List<Proyecto> obtenerProyectos();
    
    public Paginado<Proyecto> obtenerProyectosPaginado(int numeroPagina, int tamano);
    
    public void guardarProyecto(ProyectoDTO proyectoDTO);

    public Proyecto buscarProyecto(Proyecto proyecto);
    
    public void eliminarProyecto(Proyecto proyecto);

    public List<Proyecto> obtenerProyectosPorUsuario(Usuario usuario);
    
}
