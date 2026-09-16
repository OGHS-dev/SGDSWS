package com.oghs.sgdsws.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oghs.sgdsws.model.entity.BitacoraProyecto;
import com.oghs.sgdsws.model.entity.Proyecto;

public interface BitacoraProyectoRepository extends JpaRepository<BitacoraProyecto, Long> {
    List<BitacoraProyecto> findByProyectoOrderByRevisionAsc(Proyecto proyecto);
}
