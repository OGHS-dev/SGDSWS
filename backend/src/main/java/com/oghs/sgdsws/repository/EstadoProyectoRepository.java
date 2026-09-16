package com.oghs.sgdsws.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oghs.sgdsws.model.Estatus;
import com.oghs.sgdsws.model.entity.EstadoProyecto;

public interface EstadoProyectoRepository extends JpaRepository<EstadoProyecto, Long> {
    java.util.List<EstadoProyecto> findAllByEstatusOrderByDescripcionAsc(Estatus estatus);
}
