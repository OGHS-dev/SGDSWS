package com.oghs.sgdsws.application.port.out;

import java.util.List;
import java.util.Optional;

import com.oghs.sgdsws.model.entity.BitacoraProyecto;
import com.oghs.sgdsws.model.entity.Proyecto;

public interface ProjectLogPersistencePort {
    List<BitacoraProyecto> findByProject(Proyecto proyecto);
    Optional<BitacoraProyecto> findById(Long idBitacoraProyecto);
    BitacoraProyecto save(BitacoraProyecto log);
}
