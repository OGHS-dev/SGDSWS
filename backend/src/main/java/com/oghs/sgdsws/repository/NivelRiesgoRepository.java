package com.oghs.sgdsws.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oghs.sgdsws.model.Estatus;
import com.oghs.sgdsws.model.entity.NivelRiesgo;

public interface NivelRiesgoRepository extends JpaRepository<NivelRiesgo, Long> {
    List<NivelRiesgo> findAllByEstatusOrderByDescripcionAsc(Estatus estatus);
}
