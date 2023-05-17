package com.oghs.sgdsws.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.oghs.sgdsws.model.entity.Evento;
import com.oghs.sgdsws.model.repository.EventoRepository;
import com.oghs.sgdsws.util.Paginado;
import com.oghs.sgdsws.util.Paginando;

/**
 *
 * @author oghs
 */
@Service
public class EventoServiceImpl implements EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Override
    public List<Evento> obtenerEventos() {
        return (List<Evento>) eventoRepository.findAll();
    }

    @Override
    public Paginado<Evento> obtenerEventosPaginado(int numeroPagina, int tamano) {
        PageRequest pageRequest = PageRequest.of(numeroPagina - 1, tamano, Sort.by(Sort.Direction.ASC, "idEvento"));
        Page<Evento> eventosPage = (Page<Evento>) eventoRepository.findAll(pageRequest);
        return new Paginado<>(eventosPage, Paginando.of(eventosPage.getTotalPages(), numeroPagina, tamano));
    }

    @Override
    public void guardarEvento(Evento evento) {
        eventoRepository.save(evento);
    }

    @Override
    public Evento buscarEvento(Evento evento) {
        return eventoRepository.findById(evento.getIdEvento()).orElse(null);
    }

    @Override
    public void eliminarEvento(Evento evento) {
        eventoRepository.deleteById(evento.getIdEvento());
    }
    
}
