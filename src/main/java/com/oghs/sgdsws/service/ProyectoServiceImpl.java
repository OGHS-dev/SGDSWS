package com.oghs.sgdsws.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.oghs.sgdsws.model.ProyectoDTO;
import com.oghs.sgdsws.model.entity.BitacoraProyecto;
import com.oghs.sgdsws.model.entity.Proyecto;
import com.oghs.sgdsws.model.entity.Usuario;
import com.oghs.sgdsws.model.entity.UsuarioProyecto;
import com.oghs.sgdsws.model.entity.UsuarioProyectoId;
import com.oghs.sgdsws.repository.BitacoraProyectoRepository;
import com.oghs.sgdsws.repository.EstadoBitacoraProyectoRepository;
import com.oghs.sgdsws.repository.ProyectoRepository;
import com.oghs.sgdsws.repository.UsuarioProyectoRepository;
import com.oghs.sgdsws.util.Paginado;
import com.oghs.sgdsws.util.Paginando;

/**
 *
 * @author oghs
 */
@Service
public class ProyectoServiceImpl implements ProyectoService {

    @Autowired
    private ProyectoRepository proyectoRepository;

    @Autowired
    private UsuarioProyectoRepository usuarioProyectoRepository;
    
    @Autowired
    private BitacoraProyectoRepository bitacoraProyectoRepository;

    @Autowired
    private EstadoBitacoraProyectoRepository estadoBitacoraProyectoRepository;

    @Override
    public List<Proyecto> obtenerProyectos() {
        return (List<Proyecto>) proyectoRepository.findAll();
    }

    @Override
    public Paginado<Proyecto> obtenerProyectosPaginado(int numeroPagina, int tamano) {
        PageRequest pageRequest = PageRequest.of(numeroPagina - 1, tamano, Sort.by(Sort.Direction.ASC, "idProyecto"));
        Page<Proyecto> proyectosPage = (Page<Proyecto>) proyectoRepository.findAll(pageRequest);

        return new Paginado<>(proyectosPage, Paginando.of(proyectosPage.getTotalPages(), numeroPagina, tamano));
    }

    @Override
    public void guardarProyecto(ProyectoDTO proyectoDTO) {
        proyectoRepository.save(proyectoDTO.getProyecto());

        if (!Objects.isNull(proyectoDTO.getListaUsuariosProyecto())) {
            proyectoDTO.getListaUsuariosProyecto().forEach(usuario -> {
                usuarioProyectoRepository.save(new UsuarioProyecto(new UsuarioProyectoId(usuario.getIdUsuario(), proyectoDTO.getProyecto().getIdProyecto()), usuario, proyectoDTO.getProyecto()));
            });
        }
        
        List<BitacoraProyecto> listaBitacoraProyecto = bitacoraProyectoRepository.findByProyecto(proyectoDTO.getProyecto());

        BitacoraProyecto bitacoraProyecto = new BitacoraProyecto();
        bitacoraProyecto.setProyecto(proyectoDTO.getProyecto());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (listaBitacoraProyecto.isEmpty()) {
            bitacoraProyecto.setRevision(1L);
            // agregar los otros campos FK como no aplica o algo asi
            bitacoraProyecto.setEstadoBitacoraProyecto(estadoBitacoraProyectoRepository.findByCodigo("ECRE"));
            bitacoraProyecto.setDescripcion("Proyecto creado");
            bitacoraProyecto.setComponente("Proyecto");
            bitacoraProyecto.setVersion("0");
            bitacoraProyecto.setUsuarioReporte(authentication.getName());
        } else {
            bitacoraProyecto.setRevision(listaBitacoraProyecto.get(listaBitacoraProyecto.size() - 1).getRevision() + 1);
            // agregar los otros campos FK como no aplica o algo asi
            bitacoraProyecto.setEstadoBitacoraProyecto(estadoBitacoraProyectoRepository.findByCodigo("EMOD"));
            bitacoraProyecto.setDescripcion("Proyecto modificado");
            bitacoraProyecto.setComponente("Proyecto");
            bitacoraProyecto.setVersion("0");
            bitacoraProyecto.setUsuarioReporte(authentication.getName());
        }

        bitacoraProyectoRepository.save(bitacoraProyecto);
    }

    @Override
    public Proyecto buscarProyecto(Proyecto proyecto) {
        return proyectoRepository.findById(proyecto.getIdProyecto()).orElse(null);
    }

    @Override
    public void eliminarProyecto(Proyecto proyecto) {
        usuarioProyectoRepository.deleteAll(usuarioProyectoRepository.findByProyecto(proyecto));
        bitacoraProyectoRepository.deleteAll(bitacoraProyectoRepository.findByProyecto(proyecto));
        proyectoRepository.deleteById(proyecto.getIdProyecto());
    }

    @Override
    public List<Proyecto> obtenerProyectosPorUsuario(Usuario usuario) {
        List<Proyecto> listaProyectosUsuario = new ArrayList<>();
        
        usuarioProyectoRepository.findByUsuario(usuario).forEach(usuarioProyecto -> {
            listaProyectosUsuario.add(usuarioProyecto.getProyecto());
        });

        return listaProyectosUsuario;
    }

}
