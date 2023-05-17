package com.oghs.sgdsws.model.service;

import java.util.List;

import com.oghs.sgdsws.model.entity.EstadoBitacoraProyecto;
import com.oghs.sgdsws.util.Paginado;

/**
 *
 * @author oghs
 */
public interface EstadoBitacoraProyectoService {
    
    public List<EstadoBitacoraProyecto> obtenerEstadosBitacoraProyecto();

    public Paginado<EstadoBitacoraProyecto> obtenerEstadosBitacoraProyectoPaginado(int numeroPagina, int tamano);

    public void guardarEstadoBitacoraProyecto(EstadoBitacoraProyecto estadoBitacoraProyecto);

    public EstadoBitacoraProyecto buscarEstadoBitacoraProyecto(EstadoBitacoraProyecto estadoBitacoraProyecto);

    public void eliminarEstadoBitacoraProyecto(EstadoBitacoraProyecto estadoBitacoraProyecto);
    
}
