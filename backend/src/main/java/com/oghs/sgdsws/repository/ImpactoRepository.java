package com.oghs.sgdsws.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oghs.sgdsws.model.Estatus;
import com.oghs.sgdsws.model.entity.Impacto;

public interface ImpactoRepository extends JpaRepository<Impacto, Long> {
    List<Impacto> findAllByEstatusOrderByDescripcionAsc(Estatus estatus);
}
