package com.oghs.sgdsws.adapters.out.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.oghs.sgdsws.application.port.out.ProjectPersistencePort;
import com.oghs.sgdsws.model.entity.Proyecto;
import com.oghs.sgdsws.repository.ProyectoRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProjectPersistenceAdapter implements ProjectPersistencePort {
    private final ProyectoRepository proyectoRepository;

    public List<Proyecto> findAll() {
        return proyectoRepository.findAll();
    }

    public Optional<Proyecto> findById(Long idProyecto) {
        return proyectoRepository.findById(idProyecto);
    }

    public Proyecto save(Proyecto proyecto) {
        return proyectoRepository.save(proyecto);
    }

    public void deleteById(Long idProyecto) {
        proyectoRepository.deleteById(idProyecto);
    }
}
