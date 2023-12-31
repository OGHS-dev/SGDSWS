package com.oghs.sgdsws.service;

import java.util.List;

import com.oghs.sgdsws.model.entity.Prioridad;
import com.oghs.sgdsws.util.Paginado;

/**
 *
 * @author oghs
 */
public interface PrioridadService {
    
    public List<Prioridad> obtenerPrioridades();

    public Paginado<Prioridad> obtenerPrioridadesPaginado(int numeroPagina, int tamano);

    public void guardarPrioridad(Prioridad prioridad);

    public Prioridad buscarPrioridad(Prioridad prioridad);

    public void eliminarPrioridad(Prioridad prioridad);
    
}
