package com.oghs.sgdsws.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.oghs.sgdsws.model.entity.Archivo;
import com.oghs.sgdsws.model.entity.BitacoraProyecto;

/**
 *
 * @author oghs
 */
public interface ArchivoRepository extends CrudRepository<Archivo, Long> {
    
    public List<Archivo> findByBitacoraProyecto(BitacoraProyecto bitacoraProyecto);

}
