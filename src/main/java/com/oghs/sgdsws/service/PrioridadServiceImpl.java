package com.oghs.sgdsws.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.oghs.sgdsws.model.Estatus;
import com.oghs.sgdsws.model.entity.Prioridad;
import com.oghs.sgdsws.repository.PrioridadRepository;
import com.oghs.sgdsws.util.Paginado;
import com.oghs.sgdsws.util.Paginando;

/**
 *
 * @author oghs
 */
@Service
public class PrioridadServiceImpl implements PrioridadService {

    private final PrioridadRepository prioridadRepository;

    public PrioridadServiceImpl(PrioridadRepository prioridadRepository) {
        this.prioridadRepository = prioridadRepository;
    }

    @Override
    public List<Prioridad> obtenerPrioridades() {
        return prioridadRepository.findAllByEstatusOrderByCodigoAsc(Estatus.ACTIVO);
    }

    @Override
    public Paginado<Prioridad> obtenerPrioridadesPaginado(int numeroPagina, int tamano) {
        PageRequest pageRequest = PageRequest.of(numeroPagina - 1, tamano, Sort.by(Sort.Direction.ASC, "idPrioridad"));
        Page<Prioridad> prioridadesPage = prioridadRepository.findAll(pageRequest);
        
        return new Paginado<>(prioridadesPage, Paginando.of(prioridadesPage.getTotalPages(), numeroPagina, tamano));
    }

    @Override
    public void guardarPrioridad(Prioridad prioridad) {
        prioridad.setCodigo(prioridad.getCodigo().toUpperCase());

        prioridadRepository.save(prioridad);
    }

    @Override
    public Prioridad buscarPrioridad(Prioridad prioridad) {
        return prioridadRepository.findById(prioridad.getIdPrioridad()).orElse(null);
    }

    @Override
    public void eliminarPrioridad(Prioridad prioridad) {
        prioridadRepository.deleteById(prioridad.getIdPrioridad());
    }
    
}
