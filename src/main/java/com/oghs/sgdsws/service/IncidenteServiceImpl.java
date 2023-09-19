package com.oghs.sgdsws.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.oghs.sgdsws.model.entity.Incidente;
import com.oghs.sgdsws.repository.IncidenteRepository;
import com.oghs.sgdsws.util.Paginado;
import com.oghs.sgdsws.util.Paginando;

/**
 *
 * @author oghs
 */
@Service
public class IncidenteServiceImpl implements IncidenteService {

    @Autowired
    private IncidenteRepository incidenteRepository;

    @Override
    public List<Incidente> obtenerIncidentes() {
        return (List<Incidente>) incidenteRepository.findAll();
    }

    @Override
    public Paginado<Incidente> obtenerIncidentesPaginado(int numeroPagina, int tamano) {
        PageRequest pageRequest = PageRequest.of(numeroPagina - 1, tamano, Sort.by(Sort.Direction.ASC, "idIncidente"));
        Page<Incidente> incidentesPage = (Page<Incidente>) incidenteRepository.findAll(pageRequest);

        return new Paginado<>(incidentesPage, Paginando.of(incidentesPage.getTotalPages(), numeroPagina, tamano));
    }

    @Override
    public void guardarIncidente(Incidente incidente) {
        incidente.setCodigo(incidente.getCodigo().toUpperCase());

        incidenteRepository.save(incidente);
    }

    @Override
    public Incidente buscarIncidente(Incidente incidente) {
        return incidenteRepository.findById(incidente.getIdIncidente()).orElse(null);
    }

    @Override
    public void eliminarIncidente(Incidente incidente) {
        incidenteRepository.deleteById(incidente.getIdIncidente());
    }
    
}
