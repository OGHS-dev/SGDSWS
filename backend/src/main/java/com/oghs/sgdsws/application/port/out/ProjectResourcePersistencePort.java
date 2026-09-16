package com.oghs.sgdsws.application.port.out;

import java.util.List;
import java.util.Optional;

import com.oghs.sgdsws.model.entity.Archivo;
import com.oghs.sgdsws.model.entity.BitacoraProyecto;
import com.oghs.sgdsws.model.entity.Comentario;

public interface ProjectResourcePersistencePort {
    List<Archivo> findFiles(BitacoraProyecto log);
    Optional<Archivo> findFile(Long idArchivo);
    Archivo saveFile(Archivo archivo);
    void deleteFile(Archivo archivo);
    List<Comentario> findComments(BitacoraProyecto log);
    Comentario saveComment(Comentario comentario);
}
