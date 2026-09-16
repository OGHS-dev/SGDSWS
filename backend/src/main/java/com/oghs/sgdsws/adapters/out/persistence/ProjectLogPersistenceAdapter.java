package com.oghs.sgdsws.adapters.out.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.oghs.sgdsws.application.port.out.ProjectLogPersistencePort;
import com.oghs.sgdsws.model.entity.BitacoraProyecto;
import com.oghs.sgdsws.model.entity.Proyecto;
import com.oghs.sgdsws.repository.BitacoraProyectoRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProjectLogPersistenceAdapter implements ProjectLogPersistencePort {
    private final BitacoraProyectoRepository repository;

    @Override
    public List<BitacoraProyecto> findByProject(Proyecto proyecto) {
        return repository.findByProyectoOrderByRevisionAsc(proyecto);
    }

    @Override
    public Optional<BitacoraProyecto> findById(Long idBitacoraProyecto) {
        return repository.findById(idBitacoraProyecto);
    }

    @Override
    public BitacoraProyecto save(BitacoraProyecto log) {
        return repository.save(log);
    }
}
