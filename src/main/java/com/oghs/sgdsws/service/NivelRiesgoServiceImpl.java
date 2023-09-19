package com.oghs.sgdsws.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.oghs.sgdsws.model.entity.NivelRiesgo;
import com.oghs.sgdsws.repository.NivelRiesgoRepository;
import com.oghs.sgdsws.util.Paginado;
import com.oghs.sgdsws.util.Paginando;

/**
 *
 * @author oghs
 */
@Service
public class NivelRiesgoServiceImpl implements NivelRiesgoService {

    @Autowired
    private NivelRiesgoRepository nivelRiesgoRepository;

    @Override
    public List<NivelRiesgo> obtenerNivelesRiesgo() {
        return (List<NivelRiesgo>) nivelRiesgoRepository.findAll();
    }

    @Override
    public Paginado<NivelRiesgo> obtenerNivelesRiesgoPaginado(int numeroPagina, int tamano) {
        PageRequest pageRequest = PageRequest.of(numeroPagina - 1, tamano, Sort.by(Sort.Direction.ASC, "idNivelRiesgo"));
        Page<NivelRiesgo> nivelesRiesgoPage = (Page<NivelRiesgo>) nivelRiesgoRepository.findAll(pageRequest);
        
        return new Paginado<>(nivelesRiesgoPage, Paginando.of(nivelesRiesgoPage.getTotalPages(), numeroPagina, tamano));
    }

    @Override
    public void guardarNivelRiesgo(NivelRiesgo nivelRiesgo) {
        nivelRiesgo.setCodigo(nivelRiesgo.getCodigo().toUpperCase());

        nivelRiesgoRepository.save(nivelRiesgo);
    }

    @Override
    public NivelRiesgo buscarNivelRiesgo(NivelRiesgo nivelRiesgo) {
        return nivelRiesgoRepository.findById(nivelRiesgo.getIdNivelRiesgo()).orElse(null);
    }

    @Override
    public void eliminarNivelRiesgo(NivelRiesgo nivelRiesgo) {
        nivelRiesgoRepository.deleteById(nivelRiesgo.getIdNivelRiesgo());
    }
    
}
