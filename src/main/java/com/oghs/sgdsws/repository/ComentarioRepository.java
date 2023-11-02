package com.oghs.sgdsws.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.oghs.sgdsws.model.entity.BitacoraProyecto;
import com.oghs.sgdsws.model.entity.Comentario;

/**
 *
 * @author oghs
 */
public interface ComentarioRepository extends CrudRepository<Comentario, Long> {
    
    public List<Comentario> findByBitacoraProyecto(BitacoraProyecto bitacoraProyecto);
    
}
