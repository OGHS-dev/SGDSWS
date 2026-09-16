package com.oghs.sgdsws.controller;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oghs.sgdsws.application.port.in.ManageProjectsUseCase;
import com.oghs.sgdsws.dto.request.ProyectoRequest;
import com.oghs.sgdsws.dto.response.ProyectoResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/proyectos")
@RequiredArgsConstructor
public class ProyectoController {
    private final ManageProjectsUseCase manageProjectsUseCase;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR', 'AUDITOR', 'REVISOR', 'DESARROLLO')")
    public ResponseEntity<List<ProyectoResponse>> listarProyectos() {
        return ResponseEntity.ok(manageProjectsUseCase.listProjects());
    }

    @GetMapping("/{idProyecto}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR', 'AUDITOR', 'REVISOR', 'DESARROLLO')")
    public ResponseEntity<ProyectoResponse> buscarProyecto(@PathVariable Long idProyecto) {
        return ResponseEntity.ok(manageProjectsUseCase.findProject(idProyecto));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR', 'AUDITOR', 'REVISOR')")
    public ResponseEntity<ProyectoResponse> crearProyecto(@Valid @RequestBody ProyectoRequest request) {
        return ResponseEntity.status(201).body(manageProjectsUseCase.createProject(request));
    }

    @PutMapping("/{idProyecto}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR', 'AUDITOR')")
    public ResponseEntity<ProyectoResponse> actualizarProyecto(
            @PathVariable Long idProyecto, @Valid @RequestBody ProyectoRequest request) {
        return ResponseEntity.ok(manageProjectsUseCase.updateProject(idProyecto, request));
    }

    @DeleteMapping("/{idProyecto}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarProyecto(@PathVariable Long idProyecto) {
        manageProjectsUseCase.deleteProject(idProyecto);
        return ResponseEntity.noContent().build();
    }
}
