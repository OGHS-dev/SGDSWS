package com.oghs.sgdsws.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oghs.sgdsws.model.Estatus;
import com.oghs.sgdsws.model.entity.EstadoBitacoraProyecto;

public interface EstadoBitacoraProyectoRepository extends JpaRepository<EstadoBitacoraProyecto, Long> {
    List<EstadoBitacoraProyecto> findAllByEstatusOrderByDescripcionAsc(Estatus estatus);
}
