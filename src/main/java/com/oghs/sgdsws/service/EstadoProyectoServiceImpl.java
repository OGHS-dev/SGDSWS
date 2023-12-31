package com.oghs.sgdsws.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.oghs.sgdsws.model.Estatus;
import com.oghs.sgdsws.model.entity.EstadoProyecto;
import com.oghs.sgdsws.repository.EstadoProyectoRepository;
import com.oghs.sgdsws.util.Paginado;
import com.oghs.sgdsws.util.Paginando;

/**
 *
 * @author oghs
 */
@Service
public class EstadoProyectoServiceImpl implements EstadoProyectoService {

    private final EstadoProyectoRepository estadoProyectoRepository;

    public EstadoProyectoServiceImpl(EstadoProyectoRepository estadoProyectoRepository) {
        this.estadoProyectoRepository = estadoProyectoRepository;
    }

    @Override
    public List<EstadoProyecto> obtenerEstadosProyecto() {
        return estadoProyectoRepository.findAllByEstatusOrderByIdEstadoProyectoAsc(Estatus.ACTIVO);
    }

    @Override
    public Paginado<EstadoProyecto> obtenerEstadosProyectoPaginado(int numeroPagina, int tamano) {
        PageRequest pageRequest = PageRequest.of(numeroPagina - 1, tamano, Sort.by(Sort.Direction.ASC, "idEstadoProyecto"));
        Page<EstadoProyecto> estadosProyectoPage = estadoProyectoRepository.findAll(pageRequest);
        
        return new Paginado<>(estadosProyectoPage, Paginando.of(estadosProyectoPage.getTotalPages(), numeroPagina, tamano));
    }

    @Override
    public void guardarEstadoProyecto(EstadoProyecto estadoProyecto) {
        estadoProyecto.setCodigo(estadoProyecto.getCodigo().toUpperCase());

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
