package com.oghs.sgdsws.service;

import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.oghs.sgdsws.model.ProyectoDTO;
import com.oghs.sgdsws.model.entity.Archivo;
import com.oghs.sgdsws.model.entity.BitacoraProyecto;
import com.oghs.sgdsws.model.entity.Comentario;
import com.oghs.sgdsws.model.entity.Proyecto;
import com.oghs.sgdsws.repository.ArchivoRepository;
import com.oghs.sgdsws.repository.BitacoraProyectoRepository;
import com.oghs.sgdsws.repository.ComentarioRepository;
import com.oghs.sgdsws.repository.EstadoBitacoraProyectoRepository;
import com.oghs.sgdsws.util.Paginado;
import com.oghs.sgdsws.util.Paginando;

/**
 *
 * @author oghs
 */
@Service
public class BitacoraProyectoServiceImpl implements BitacoraProyectoService {

    private final BitacoraProyectoRepository bitacoraProyectoRepository;

    private final EstadoBitacoraProyectoRepository estadoBitacoraProyectoRepository;

    private final ArchivoRepository archivoRepository;

    private final ComentarioRepository comentarioRepository;

    public BitacoraProyectoServiceImpl(BitacoraProyectoRepository bitacoraProyectoRepository, EstadoBitacoraProyectoRepository estadoBitacoraProyectoRepository, ArchivoRepository archivoRepository, ComentarioRepository comentarioRepository) {
        this.bitacoraProyectoRepository = bitacoraProyectoRepository;
        this.estadoBitacoraProyectoRepository = estadoBitacoraProyectoRepository;
        this.archivoRepository = archivoRepository;
        this.comentarioRepository = comentarioRepository;
    }

    @Override
    public List<BitacoraProyecto> obtenerBitacoraProyectoPorProyecto(Proyecto proyecto) {
        return bitacoraProyectoRepository.findByProyecto(proyecto);
    }

    @Override
    public Paginado<BitacoraProyecto> obtenerBitacoraProyectoPorProyectoPaginado(Proyecto proyecto, int numeroPagina, int tamano) {
        PageRequest pageRequest = PageRequest.of(numeroPagina - 1, tamano, Sort.by(Sort.Direction.ASC, "idBitacoraProyecto"));
        Page<BitacoraProyecto> bitacoraProyectoPage = bitacoraProyectoRepository.findByProyecto(proyecto, pageRequest);
        
        return new Paginado<>(bitacoraProyectoPage, Paginando.of(bitacoraProyectoPage.getTotalPages(), numeroPagina, tamano));
    }

    @Override
    public void guardarBitacoraProyecto(ProyectoDTO proyectoDTO, List<Archivo> archivos) {
        List<BitacoraProyecto> listaBitacoraProyecto = bitacoraProyectoRepository.findByProyecto(proyectoDTO.getBitacoraProyecto().getProyecto());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        proyectoDTO.getBitacoraProyecto().setRevision(listaBitacoraProyecto.get(listaBitacoraProyecto.size() - 1).getRevision() + 1);
        proyectoDTO.getBitacoraProyecto().setUsuarioReporte(authentication.getName());

        if (Objects.isNull(proyectoDTO.getBitacoraProyecto().getEstadoBitacoraProyecto()) ) {
            proyectoDTO.getBitacoraProyecto().setEstadoBitacoraProyecto(estadoBitacoraProyectoRepository.findByCodigo("EABI"));
        }

        bitacoraProyectoRepository.save(proyectoDTO.getBitacoraProyecto());

        if (!archivos.isEmpty()) {
            archivos.forEach(archivo -> {
                archivo.setBitacoraProyecto(proyectoDTO.getBitacoraProyecto());
                archivo.setUsuarioCreacion(authentication.getName());
                archivoRepository.save(archivo);
            });
        }

        if (!proyectoDTO.getComentario().getComentario().isBlank()) {
            Comentario comentario = new Comentario();
            comentario.setBitacoraProyecto(proyectoDTO.getBitacoraProyecto());
            comentario.setComentario(proyectoDTO.getComentario().getComentario());
            comentario.setUsuarioCreacion(authentication.getName());
            comentarioRepository.save(comentario);
        }
    }

    @Override
    public BitacoraProyecto buscarBitacoraProyecto(BitacoraProyecto bitacoraProyecto) {
        return bitacoraProyectoRepository.findById(bitacoraProyecto.getIdBitacoraProyecto()).orElse(null);
    }

    @Override
    public List<BitacoraProyecto> buscarBitacoraProyectoPorUsuarioAsignado(String usuarioAsignado) {
        return bitacoraProyectoRepository.findByUsuarioAsignado(usuarioAsignado);
    }
    
}
