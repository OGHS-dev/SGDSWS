package com.oghs.sgdsws.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.oghs.sgdsws.model.Frecuencia;
import com.oghs.sgdsws.model.ProyectoDTO;
import com.oghs.sgdsws.model.entity.Archivo;
import com.oghs.sgdsws.model.entity.BitacoraProyecto;
import com.oghs.sgdsws.model.entity.Comentario;
import com.oghs.sgdsws.model.entity.Proyecto;
import com.oghs.sgdsws.model.entity.Usuario;
import com.oghs.sgdsws.model.entity.UsuarioProyecto;
import com.oghs.sgdsws.model.entity.UsuarioProyectoId;
import com.oghs.sgdsws.repository.ArchivoRepository;
import com.oghs.sgdsws.repository.BitacoraProyectoRepository;
import com.oghs.sgdsws.repository.CategoriaRepository;
import com.oghs.sgdsws.repository.ComentarioRepository;
import com.oghs.sgdsws.repository.EstadoBitacoraProyectoRepository;
import com.oghs.sgdsws.repository.HallazgoRepository;
import com.oghs.sgdsws.repository.ImpactoRepository;
import com.oghs.sgdsws.repository.IncidenteRepository;
import com.oghs.sgdsws.repository.ModuloRepository;
import com.oghs.sgdsws.repository.NivelRiesgoRepository;
import com.oghs.sgdsws.repository.PrioridadRepository;
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

    private final ProyectoRepository proyectoRepository;

    private final UsuarioProyectoRepository usuarioProyectoRepository;
    
    private final BitacoraProyectoRepository bitacoraProyectoRepository;

    private final ModuloRepository moduloRepository;

    private final HallazgoRepository hallazgoRepository;

    private final IncidenteRepository incidenteRepository;

    private final CategoriaRepository categoriaRepository;

    private final PrioridadRepository prioridadRepository;

    private final ImpactoRepository impactoRepository;

    private final NivelRiesgoRepository nivelRiesgoRepository;

    private final EstadoBitacoraProyectoRepository estadoBitacoraProyectoRepository;

    private final ArchivoRepository archivoRepository;

    private final ComentarioRepository comentarioRepository;

    public ProyectoServiceImpl(ProyectoRepository proyectoRepository, UsuarioProyectoRepository usuarioProyectoRepository, BitacoraProyectoRepository bitacoraProyectoRepository, ModuloRepository moduloRepository, HallazgoRepository hallazgoRepository, IncidenteRepository incidenteRepository, CategoriaRepository categoriaRepository, PrioridadRepository prioridadRepository, ImpactoRepository impactoRepository, NivelRiesgoRepository nivelRiesgoRepository, EstadoBitacoraProyectoRepository estadoBitacoraProyectoRepository, ArchivoRepository archivoRepository, ComentarioRepository comentarioRepository) {
        this.proyectoRepository = proyectoRepository;
        this.usuarioProyectoRepository = usuarioProyectoRepository;
        this.bitacoraProyectoRepository = bitacoraProyectoRepository;
        this.moduloRepository = moduloRepository;
        this.hallazgoRepository = hallazgoRepository;
        this.incidenteRepository = incidenteRepository;
        this.categoriaRepository = categoriaRepository;
        this.prioridadRepository = prioridadRepository;
        this.impactoRepository = impactoRepository;
        this.nivelRiesgoRepository = nivelRiesgoRepository;
        this.estadoBitacoraProyectoRepository = estadoBitacoraProyectoRepository;
        this.archivoRepository = archivoRepository;
        this.comentarioRepository = comentarioRepository;
    }

    @Override
    public List<Proyecto> obtenerProyectos() {
        return (List<Proyecto>) proyectoRepository.findAll();
    }

    @Override
    public Paginado<Proyecto> obtenerProyectosPaginado(int numeroPagina, int tamano) {
        PageRequest pageRequest = PageRequest.of(numeroPagina - 1, tamano, Sort.by(Sort.Direction.ASC, "idProyecto"));
        Page<Proyecto> proyectosPage = proyectoRepository.findAll(pageRequest);

        return new Paginado<>(proyectosPage, Paginando.of(proyectosPage.getTotalPages(), numeroPagina, tamano));
    }

    @Override
    public void guardarProyecto(ProyectoDTO proyectoDTO) {
        proyectoRepository.save(proyectoDTO.getProyecto());

        if (!Objects.isNull(proyectoDTO.getListaUsuariosProyecto())) {
            proyectoDTO.getListaUsuariosProyecto().forEach(usuario -> usuarioProyectoRepository.save(new UsuarioProyecto(new UsuarioProyectoId(usuario.getIdUsuario(), proyectoDTO.getProyecto().getIdProyecto()), usuario, proyectoDTO.getProyecto())));
        }
        
        List<BitacoraProyecto> listaBitacoraProyecto = bitacoraProyectoRepository.findByProyecto(proyectoDTO.getProyecto());

        BitacoraProyecto bitacoraProyecto = new BitacoraProyecto();
        bitacoraProyecto.setProyecto(proyectoDTO.getProyecto());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (listaBitacoraProyecto.isEmpty()) {
            bitacoraProyecto.setRevision(1L);
            bitacoraProyecto.setUsuarioReporte(authentication.getName());
            bitacoraProyecto.setModulo(moduloRepository.findByCodigo("MOAP"));
            bitacoraProyecto.setHallazgo(hallazgoRepository.findByCodigo("HANI"));
            bitacoraProyecto.setIncidente(incidenteRepository.findByCodigo("INNI"));
            bitacoraProyecto.setCategoria(categoriaRepository.findByCodigo("CGEN"));
            bitacoraProyecto.setPrioridad(prioridadRepository.findByCodigo("PNIN"));
            bitacoraProyecto.setImpacto(impactoRepository.findByCodigo("ININ"));
            bitacoraProyecto.setNivelRiesgo(nivelRiesgoRepository.findByCodigo("NRNI"));
            bitacoraProyecto.setEstadoBitacoraProyecto(estadoBitacoraProyectoRepository.findByCodigo("ECRE"));
            bitacoraProyecto.setDescripcion("Proyecto creado");
            bitacoraProyecto.setComponente("Proyecto");
            bitacoraProyecto.setVersion("0");
            bitacoraProyecto.setFrecuencia(Frecuencia.NO_APLICA.getEtiqueta());
        } else {
            bitacoraProyecto.setRevision(listaBitacoraProyecto.get(listaBitacoraProyecto.size() - 1).getRevision() + 1);
            bitacoraProyecto.setUsuarioReporte(authentication.getName());
            bitacoraProyecto.setModulo(moduloRepository.findByCodigo("MOAP"));
            bitacoraProyecto.setHallazgo(hallazgoRepository.findByCodigo("HANI"));
            bitacoraProyecto.setIncidente(incidenteRepository.findByCodigo("INNI"));
            bitacoraProyecto.setCategoria(categoriaRepository.findByCodigo("CGEN"));
            bitacoraProyecto.setPrioridad(prioridadRepository.findByCodigo("PNIN"));
            bitacoraProyecto.setImpacto(impactoRepository.findByCodigo("ININ"));
            bitacoraProyecto.setNivelRiesgo(nivelRiesgoRepository.findByCodigo("NRNI"));
            bitacoraProyecto.setEstadoBitacoraProyecto(estadoBitacoraProyectoRepository.findByCodigo("EMOD"));
            bitacoraProyecto.setDescripcion("Proyecto modificado");
            bitacoraProyecto.setComponente("Proyecto");
            bitacoraProyecto.setVersion("0");
            bitacoraProyecto.setFrecuencia(Frecuencia.NO_APLICA.getEtiqueta());
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

        proyecto.getBitacoraProyecto().forEach(bp -> {
            List<Archivo> archivos = archivoRepository.findByBitacoraProyecto(bp);
            if (!archivos.isEmpty()) {
                archivoRepository.deleteAll(archivos);
            }

            List<Comentario> comentarios = comentarioRepository.findByBitacoraProyecto(bp);
            if (!comentarios.isEmpty()) {
                comentarioRepository.deleteAll(comentarios);
            }
        });

        bitacoraProyectoRepository.deleteAll(proyecto.getBitacoraProyecto());
        
        proyectoRepository.deleteById(proyecto.getIdProyecto());
    }

    @Override
    public List<Proyecto> obtenerProyectosPorUsuario(Usuario usuario) {
        List<Proyecto> listaProyectosUsuario = new ArrayList<>();
        
        usuarioProyectoRepository.findByUsuario(usuario).forEach(usuarioProyecto -> listaProyectosUsuario.add(usuarioProyecto.getProyecto()));

        return listaProyectosUsuario;
    }

}
