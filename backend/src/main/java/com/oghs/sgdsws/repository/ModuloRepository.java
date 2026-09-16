package com.oghs.sgdsws.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oghs.sgdsws.model.Estatus;
import com.oghs.sgdsws.model.entity.Modulo;

public interface ModuloRepository extends JpaRepository<Modulo, Long> {
    List<Modulo> findAllByEstatusOrderByDescripcionAsc(Estatus estatus);
}
