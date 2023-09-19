package com.oghs.sgdsws.service;

import java.util.List;

import com.oghs.sgdsws.model.entity.Incidente;
import com.oghs.sgdsws.util.Paginado;

/**
 *
 * @author oghs
 */
public interface IncidenteService {
    
    public List<Incidente> obtenerIncidentes();

    public Paginado<Incidente> obtenerIncidentesPaginado(int numeroPagina, int tamano);

    public void guardarIncidente(Incidente incidente);

    public Incidente buscarIncidente(Incidente incidente);

    public void eliminarIncidente(Incidente incidente);
    
}
