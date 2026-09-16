package com.oghs.sgdsws.application.port.out;

import java.util.List;
import java.util.Optional;

import com.oghs.sgdsws.model.entity.Proyecto;

public interface ProjectPersistencePort {
    List<Proyecto> findAll();
    Optional<Proyecto> findById(Long idProyecto);
    Proyecto save(Proyecto proyecto);
    void deleteById(Long idProyecto);
}
