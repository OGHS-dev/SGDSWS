package com.oghs.sgdsws.application.service;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oghs.sgdsws.application.port.in.ManageProjectLogsUseCase;
import com.oghs.sgdsws.application.port.out.CurrentUserPort;
import com.oghs.sgdsws.application.port.out.ProjectLogPersistencePort;
import com.oghs.sgdsws.dto.request.BitacoraProyectoRequest;
import com.oghs.sgdsws.dto.request.EstadoProyectoRequest;
import com.oghs.sgdsws.dto.response.BitacoraProyectoResponse;
import com.oghs.sgdsws.dto.response.CatalogoResponse;
import com.oghs.sgdsws.exceptionhandler.ResourceNotFoundException;
import com.oghs.sgdsws.model.entity.BitacoraProyecto;
import com.oghs.sgdsws.model.entity.Proyecto;
import com.oghs.sgdsws.repository.CategoriaRepository;
import com.oghs.sgdsws.repository.EstadoBitacoraProyectoRepository;
import com.oghs.sgdsws.repository.EstadoProyectoRepository;
import com.oghs.sgdsws.repository.HallazgoRepository;
import com.oghs.sgdsws.repository.ImpactoRepository;
import com.oghs.sgdsws.repository.IncidenteRepository;
import com.oghs.sgdsws.repository.ModuloRepository;
import com.oghs.sgdsws.repository.NivelRiesgoRepository;
import com.oghs.sgdsws.repository.PrioridadRepository;
import com.oghs.sgdsws.repository.ProyectoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectLogService implements ManageProjectLogsUseCase {
    private final ProjectLogPersistencePort logPersistencePort;
    private final ProyectoRepository projectRepository;
    private final EstadoProyectoRepository stateRepository;
    private final ModuloRepository moduloRepository;
    private final HallazgoRepository hallazgoRepository;
    private final IncidenteRepository incidenteRepository;
    private final CategoriaRepository categoriaRepository;
    private final PrioridadRepository prioridadRepository;
    private final ImpactoRepository impactoRepository;
    private final NivelRiesgoRepository nivelRiesgoRepository;
    private final EstadoBitacoraProyectoRepository logStateRepository;
    private final CurrentUserPort currentUserPort;

    @Override
    @Transactional(readOnly = true)
    public List<BitacoraProyectoResponse> list(Long idProyecto) {
        return logPersistencePort.findByProject(findProject(idProyecto)).stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional
    public BitacoraProyectoResponse create(Long idProyecto, BitacoraProyectoRequest request) {
        Proyecto project = findProject(idProyecto);
        List<BitacoraProyecto> logs = logPersistencePort.findByProject(project);
        BitacoraProyecto log = new BitacoraProyecto();
        log.setProyecto(project);
        log.setRevision(logs.stream().map(BitacoraProyecto::getRevision).filter(java.util.Objects::nonNull)
                .max(Long::compareTo).orElse(0L) + 1);
        log.setUsuarioReporte(currentUserPort.username());
        log.setDescripcion(request.descripcion());
        log.setComponente(request.componente());
        log.setVersion(request.version());
        log.setFrecuencia(request.frecuencia());
        log.setAcciones(request.acciones());
        log.setUsuarioAsignado(request.usuarioAsignado());
        log.setModulo(findById(request.idModulo(), moduloRepository::findById, "Módulo"));
        log.setHallazgo(findById(request.idHallazgo(), hallazgoRepository::findById, "Hallazgo"));
        log.setIncidente(findById(request.idIncidente(), incidenteRepository::findById, "Incidente"));
        log.setCategoria(findById(request.idCategoria(), categoriaRepository::findById, "Categoría"));
        log.setPrioridad(findById(request.idPrioridad(), prioridadRepository::findById, "Prioridad"));
        log.setImpacto(findById(request.idImpacto(), impactoRepository::findById, "Impacto"));
        log.setNivelRiesgo(findById(request.idNivelRiesgo(), nivelRiesgoRepository::findById, "Nivel de riesgo"));
        log.setEstadoBitacoraProyecto(findById(request.idEstadoBitacoraProyecto(),
                logStateRepository::findById, "Estado de bitácora"));
        return toResponse(logPersistencePort.save(log));
    }

    @Override
    @Transactional
    public void updateProjectState(Long idProyecto, EstadoProyectoRequest request) {
        Proyecto project = findProject(idProyecto);
        boolean hasOpenLogs = logPersistencePort.findByProject(project).stream()
                .map(BitacoraProyecto::getEstadoBitacoraProyecto)
                .filter(java.util.Objects::nonNull)
                .map(state -> state.getCodigo())
                .anyMatch(code -> !java.util.Set.of("ECRE", "EMOD", "ECOM", "ECER").contains(code));
        if (hasOpenLogs) {
            throw new IllegalStateException("El proyecto tiene eventos pendientes.");
        }
        project.setEstadoProyecto(stateRepository.findById(request.idEstadoProyecto())
                .orElseThrow(() -> new ResourceNotFoundException("Estado de proyecto no encontrado: " + request.idEstadoProyecto())));
        projectRepository.save(project);
    }

    private Proyecto findProject(Long idProyecto) {
        return projectRepository.findById(idProyecto)
                .orElseThrow(() -> new ResourceNotFoundException("Proyecto no encontrado: " + idProyecto));
    }

    private <T> T findById(Long id, Function<Long, java.util.Optional<T>> finder, String label) {
        return id == null ? null : finder.apply(id)
                .orElseThrow(() -> new ResourceNotFoundException(label + " no encontrado: " + id));
    }

    private BitacoraProyectoResponse toResponse(BitacoraProyecto log) {
        return new BitacoraProyectoResponse(log.getIdBitacoraProyecto(), log.getProyecto().getIdProyecto(),
                log.getFechaBitacora() == null ? null : log.getFechaBitacora().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
                log.getUsuarioReporte(), log.getRevision(), log.getDescripcion(), log.getComponente(), log.getVersion(),
                log.getFrecuencia(), catalog(log.getModulo(), item -> new CatalogoResponse(item.getIdModulo(), item.getCodigo(), item.getDescripcion())),
                catalog(log.getHallazgo(), item -> new CatalogoResponse(item.getIdHallazgo(), item.getCodigo(), item.getDescripcion())),
                catalog(log.getIncidente(), item -> new CatalogoResponse(item.getIdIncidente(), item.getCodigo(), item.getDescripcion())),
                catalog(log.getCategoria(), item -> new CatalogoResponse(item.getIdCategoria(), item.getCodigo(), item.getDescripcion())),
                catalog(log.getPrioridad(), item -> new CatalogoResponse(item.getIdPrioridad(), item.getCodigo(), item.getDescripcion())),
                catalog(log.getImpacto(), item -> new CatalogoResponse(item.getIdImpacto(), item.getCodigo(), item.getDescripcion())),
                catalog(log.getNivelRiesgo(), item -> new CatalogoResponse(item.getIdNivelRiesgo(), item.getCodigo(), item.getDescripcion())),
                catalog(log.getEstadoBitacoraProyecto(), item -> new CatalogoResponse(item.getIdEstadoBitacoraProyecto(), item.getCodigo(), item.getDescripcion())),
                log.getAcciones(), log.getUsuarioAsignado());
    }

    private <T> CatalogoResponse catalog(T entity, Function<T, CatalogoResponse> mapper) {
        return entity == null ? null : mapper.apply(entity);
    }
}
