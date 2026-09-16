package com.oghs.sgdsws.adapters.out.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.oghs.sgdsws.application.port.out.ProjectResourcePersistencePort;
import com.oghs.sgdsws.model.entity.Archivo;
import com.oghs.sgdsws.model.entity.BitacoraProyecto;
import com.oghs.sgdsws.model.entity.Comentario;
import com.oghs.sgdsws.repository.ArchivoRepository;
import com.oghs.sgdsws.repository.ComentarioRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProjectResourcePersistenceAdapter implements ProjectResourcePersistencePort {
    private final ArchivoRepository archivoRepository;
    private final ComentarioRepository comentarioRepository;

    @Override
    public List<Archivo> findFiles(BitacoraProyecto log) {
        return archivoRepository.findByBitacoraProyectoOrderByFechaCreacionAsc(log);
    }

    @Override
    public Optional<Archivo> findFile(Long idArchivo) {
        return archivoRepository.findById(idArchivo);
    }

    @Override
    public Archivo saveFile(Archivo archivo) {
        return archivoRepository.save(archivo);
    }

    @Override
    public void deleteFile(Archivo archivo) {
        archivoRepository.delete(archivo);
    }

    @Override
    public List<Comentario> findComments(BitacoraProyecto log) {
        return comentarioRepository.findByBitacoraProyectoOrderByFechaCreacionAsc(log);
    }

    @Override
    public Comentario saveComment(Comentario comentario) {
        return comentarioRepository.save(comentario);
    }
}
