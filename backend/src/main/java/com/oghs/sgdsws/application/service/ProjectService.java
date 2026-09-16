package com.oghs.sgdsws.application.service;

import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oghs.sgdsws.application.port.in.ManageProjectsUseCase;
import com.oghs.sgdsws.application.port.out.ProjectPersistencePort;
import com.oghs.sgdsws.dto.request.ProyectoRequest;
import com.oghs.sgdsws.dto.response.ProyectoResponse;
import com.oghs.sgdsws.exceptionhandler.ResourceNotFoundException;
import com.oghs.sgdsws.model.entity.EstadoProyecto;
import com.oghs.sgdsws.model.entity.Proyecto;
import com.oghs.sgdsws.model.entity.Usuario;
import com.oghs.sgdsws.repository.EstadoProyectoRepository;
import com.oghs.sgdsws.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectService implements ManageProjectsUseCase {
    private final ProjectPersistencePort projectPersistencePort;
    private final EstadoProyectoRepository estadoProyectoRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ProyectoResponse> listProjects() {
        return projectPersistencePort.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProyectoResponse findProject(Long idProyecto) {
        return toResponse(findEntity(idProyecto));
    }

    @Override
    @Transactional
    public ProyectoResponse createProject(ProyectoRequest request) {
        return toResponse(projectPersistencePort.save(toEntity(new Proyecto(), request)));
    }

    @Override
    @Transactional
    public ProyectoResponse updateProject(Long idProyecto, ProyectoRequest request) {
        return toResponse(projectPersistencePort.save(toEntity(findEntity(idProyecto), request)));
    }

    @Override
    @Transactional
    public void deleteProject(Long idProyecto) {
        findEntity(idProyecto);
        projectPersistencePort.deleteById(idProyecto);
    }

    private Proyecto findEntity(Long idProyecto) {
        return projectPersistencePort.findById(idProyecto)
                .orElseThrow(() -> new ResourceNotFoundException("Proyecto no encontrado: " + idProyecto));
    }

    private Proyecto toEntity(Proyecto project, ProyectoRequest request) {
        EstadoProyecto state = estadoProyectoRepository.findById(request.idEstadoProyecto())
                .orElseThrow(() -> new ResourceNotFoundException("Estado de proyecto no encontrado: " + request.idEstadoProyecto()));
        Set<Usuario> users = request.idsUsuarios() == null ? new HashSet<>()
                : new HashSet<>(usuarioRepository.findAllById(request.idsUsuarios()));
        project.setNombre(request.nombre());
        project.setDescripcion(request.descripcion());
        project.setEstadoProyecto(state);
        project.setResponsable(request.responsable());
        project.setFechaInicio(toDate(request.fechaInicio()));
        project.setFechaFin(toDate(request.fechaFin()));
        project.setUsuarios(users);
        return project;
    }

    private ProyectoResponse toResponse(Proyecto project) {
        return new ProyectoResponse(
                project.getIdProyecto(),
                project.getNombre(),
                project.getDescripcion(),
                project.getEstadoProyecto().getIdEstadoProyecto(),
                project.getEstadoProyecto().getDescripcion(),
                project.getResponsable(),
                toLocalDate(project.getFechaInicio()),
                toLocalDate(project.getFechaFin()),
                project.getUsuarios().stream().map(Usuario::getIdUsuario).collect(java.util.stream.Collectors.toSet()));
    }

    private Date toDate(java.time.LocalDate date) {
        return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    private java.time.LocalDate toLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
