package com.oghs.sgdsws.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oghs.sgdsws.model.Estatus;
import com.oghs.sgdsws.model.entity.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    List<Categoria> findAllByEstatusOrderByDescripcionAsc(Estatus estatus);
}
