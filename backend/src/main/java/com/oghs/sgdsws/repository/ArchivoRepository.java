package com.oghs.sgdsws.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oghs.sgdsws.model.entity.Archivo;
import com.oghs.sgdsws.model.entity.BitacoraProyecto;

public interface ArchivoRepository extends JpaRepository<Archivo, Long> {
    List<Archivo> findByBitacoraProyectoOrderByFechaCreacionAsc(BitacoraProyecto bitacoraProyecto);
}
