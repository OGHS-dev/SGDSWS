package com.oghs.sgdsws.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oghs.sgdsws.model.Estatus;
import com.oghs.sgdsws.model.entity.Prioridad;

public interface PrioridadRepository extends JpaRepository<Prioridad, Long> {
    List<Prioridad> findAllByEstatusOrderByDescripcionAsc(Estatus estatus);
}
