package com.oghs.sgdsws.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.oghs.sgdsws.model.entity.EstadoProyecto;
import com.oghs.sgdsws.model.repository.EstadoProyectoRepository;
import com.oghs.sgdsws.util.Paginado;
import com.oghs.sgdsws.util.Paginando;

/**
 *
 * @author oghs
 */
@Service
public class EstadoProyectoServiceImpl implements EstadoProyectoService {

    @Autowired
    private EstadoProyectoRepository estadoProyectoRepository;

    @Override
    public List<EstadoProyecto> obtenerEstadosProyecto() {
        return (List<EstadoProyecto>) estadoProyectoRepository.findAll();
    }

    @Override
    public Paginado<EstadoProyecto> obtenerEstadosProyectoPaginado(int numeroPagina, int tamano) {
        PageRequest pageRequest = PageRequest.of(numeroPagina - 1, tamano, Sort.by(Sort.Direction.ASC, "idEstadoProyecto"));
        Page<EstadoProyecto> estadosProyectoPage = (Page<EstadoProyecto>) estadoProyectoRepository.findAll(pageRequest);
        return new Paginado<>(estadosProyectoPage, Paginando.of(estadosProyectoPage.getTotalPages(), numeroPagina, tamano));
    }

    @Override
    public void guardarEstadoProyecto(EstadoProyecto estadoProyecto) {
        estadoProyectoRepository.save(estadoProyecto);
    }

    @Override
    public EstadoProyecto buscarEstadoProyecto(EstadoProyecto estadoProyecto) {
        return estadoProyectoRepository.findById(estadoProyecto.getIdEstadoProyecto()).orElse(null);
    }

    @Override
    public void eliminarEstadoProyecto(EstadoProyecto estadoProyecto) {
        estadoProyectoRepository.deleteById(estadoProyecto.getIdEstadoProyecto());
    }
    
}
