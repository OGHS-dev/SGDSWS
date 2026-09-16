package com.oghs.sgdsws.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oghs.sgdsws.model.Estatus;
import com.oghs.sgdsws.model.entity.Hallazgo;

public interface HallazgoRepository extends JpaRepository<Hallazgo, Long> {
    List<Hallazgo> findAllByEstatusOrderByDescripcionAsc(Estatus estatus);
}
