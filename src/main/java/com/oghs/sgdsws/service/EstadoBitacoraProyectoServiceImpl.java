package com.oghs.sgdsws.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.oghs.sgdsws.model.Estatus;
import com.oghs.sgdsws.model.entity.EstadoBitacoraProyecto;
import com.oghs.sgdsws.repository.EstadoBitacoraProyectoRepository;
import com.oghs.sgdsws.util.Paginado;
import com.oghs.sgdsws.util.Paginando;

/**
 *
 * @author oghs
 */
@Service
public class EstadoBitacoraProyectoServiceImpl implements EstadoBitacoraProyectoService {

    private final EstadoBitacoraProyectoRepository estadoBitacoraProyectoRepository;

    public EstadoBitacoraProyectoServiceImpl(EstadoBitacoraProyectoRepository estadoBitacoraProyectoRepository) {
        this.estadoBitacoraProyectoRepository = estadoBitacoraProyectoRepository;
    }

    @Override
    public List<EstadoBitacoraProyecto> obtenerEstadosBitacoraProyecto() {
        return estadoBitacoraProyectoRepository.findAllByEstatusOrderByCodigoAsc(Estatus.ACTIVO);
    }

    @Override
    public Paginado<EstadoBitacoraProyecto> obtenerEstadosBitacoraProyectoPaginado(int numeroPagina, int tamano) {
        PageRequest pageRequest = PageRequest.of(numeroPagina - 1, tamano, Sort.by(Sort.Direction.ASC, "idEstadoBitacoraProyecto"));
        Page<EstadoBitacoraProyecto> estadosBitacoraProyectoPage = estadoBitacoraProyectoRepository.findAll(pageRequest);
        
        return new Paginado<>(estadosBitacoraProyectoPage, Paginando.of(estadosBitacoraProyectoPage.getTotalPages(), numeroPagina, tamano));
    }

    @Override
    public void guardarEstadoBitacoraProyecto(EstadoBitacoraProyecto estadoBitacoraProyecto) {
        estadoBitacoraProyecto.setCodigo(estadoBitacoraProyecto.getCodigo().toUpperCase());

        estadoBitacoraProyectoRepository.save(estadoBitacoraProyecto);
    }

    @Override
    public EstadoBitacoraProyecto buscarEstadoBitacoraProyecto(EstadoBitacoraProyecto estadoBitacoraProyecto) {
        return estadoBitacoraProyectoRepository.findById(estadoBitacoraProyecto.getIdEstadoBitacoraProyecto()).orElse(null);
    }

    @Override
    public void eliminarEstadoBitacoraProyecto(EstadoBitacoraProyecto estadoBitacoraProyecto) {
        estadoBitacoraProyectoRepository.deleteById(estadoBitacoraProyecto.getIdEstadoBitacoraProyecto());
    }
    
}
