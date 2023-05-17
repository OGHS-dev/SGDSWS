package com.oghs.sgdsws.model.service;

import java.util.List;

import com.oghs.sgdsws.model.entity.EstadoProyecto;
import com.oghs.sgdsws.util.Paginado;

/**
 *
 * @author oghs
 */
public interface EstadoProyectoService {
    
    public List<EstadoProyecto> obtenerEstadosProyecto();

    public Paginado<EstadoProyecto> obtenerEstadosProyectoPaginado(int numeroPagina, int tamano);

    public void guardarEstadoProyecto(EstadoProyecto estadoProyecto);

    public EstadoProyecto buscarEstadoProyecto(EstadoProyecto estadoProyecto);

    public void eliminarEstadoProyecto(EstadoProyecto estadoProyecto);
    
}
