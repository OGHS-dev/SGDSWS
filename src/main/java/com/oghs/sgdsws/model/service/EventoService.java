package com.oghs.sgdsws.model.service;

import java.util.List;

import com.oghs.sgdsws.model.entity.Evento;
import com.oghs.sgdsws.util.Paginado;

/**
 *
 * @author oghs
 */
public interface EventoService {
    
    public List<Evento> obtenerEventos();

    public Paginado<Evento> obtenerEventosPaginado(int numeroPagina, int tamano);

    public void guardarEvento(Evento evento);

    public Evento buscarEvento(Evento evento);

    public void eliminarEvento(Evento evento);
    
}
