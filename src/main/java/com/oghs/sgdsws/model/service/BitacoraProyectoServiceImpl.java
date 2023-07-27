package com.oghs.sgdsws.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.oghs.sgdsws.model.repository.ArchivoRepository;
import com.oghs.sgdsws.model.repository.BitacoraProyectoRepository;
import com.oghs.sgdsws.model.repository.ComentarioRepository;
import com.oghs.sgdsws.util.Paginado;
import com.oghs.sgdsws.util.Paginando;

/**
 *
 * @author oghs
 */
@Service
public class BitacoraProyectoServiceImpl implements BitacoraProyectoService {

    @Autowired
    private BitacoraProyectoRepository bitacoraProyectoRepository;

    @Autowired
    private ArchivoRepository archivoRepository;

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Override
    public List<BitacoraProyecto> obtenerBitacoraProyectoPorProyecto(Proyecto proyecto) {
        return bitacoraProyectoRepository.findByProyecto(proyecto);
    }

    @Override
    public Paginado<BitacoraProyecto> obtenerBitacoraProyectoPorProyectoPaginado(Proyecto proyecto, int numeroPagina, int tamano) {
        PageRequest pageRequest = PageRequest.of(numeroPagina - 1, tamano, Sort.by(Sort.Direction.ASC, "idBitacoraProyecto"));
        Page<BitacoraProyecto> bitacoraProyectoPage = (Page<BitacoraProyecto>) bitacoraProyectoRepository.findByProyecto(proyecto, pageRequest);
        
        return new Paginado<>(bitacoraProyectoPage, Paginando.of(bitacoraProyectoPage.getTotalPages(), numeroPagina, tamano));
    }

    @Override
    public void guardarBitacoraProyecto(ProyectoDTO proyectoDTO, List<Archivo> archivos) {
        List<BitacoraProyecto> listaBitacoraProyecto = bitacoraProyectoRepository.findByProyecto(proyectoDTO.getBitacoraProyecto().getProyecto());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        proyectoDTO.getBitacoraProyecto().setRevision(listaBitacoraProyecto.get(listaBitacoraProyecto.size() - 1).getRevision() + 1);
        proyectoDTO.getBitacoraProyecto().setUsuarioReporte(authentication.getName());
        bitacoraProyectoRepository.save(proyectoDTO.getBitacoraProyecto());

        if (!archivos.isEmpty()) {
            System.out.println(archivos.size());
            for (Archivo archivo : archivos) {
                archivo.setBitacoraProyecto(proyectoDTO.getBitacoraProyecto());
                archivoRepository.save(archivo);
            }
        }

        if (proyectoDTO.getComentario().getComentario() != null) {
            Comentario comentario = new Comentario();
            comentario.setBitacoraProyecto(proyectoDTO.getBitacoraProyecto());
            comentario.setComentario(proyectoDTO.getComentario().getComentario());
            comentarioRepository.save(comentario);
        }
    }

    @Override
    public BitacoraProyecto buscarBitacoraProyecto(BitacoraProyecto bitacoraProyecto) {
        return bitacoraProyectoRepository.findById(bitacoraProyecto.getIdBitacoraProyecto()).orElse(null);
    }
    
}
