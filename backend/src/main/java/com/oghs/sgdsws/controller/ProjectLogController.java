package com.oghs.sgdsws.controller;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oghs.sgdsws.application.port.in.ManageProjectLogsUseCase;
import com.oghs.sgdsws.dto.request.BitacoraProyectoRequest;
import com.oghs.sgdsws.dto.request.EstadoProyectoRequest;
import com.oghs.sgdsws.dto.response.BitacoraProyectoResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/proyectos/{idProyecto}/bitacora")
@RequiredArgsConstructor
public class ProjectLogController {
    private final ManageProjectLogsUseCase manageProjectLogsUseCase;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR', 'AUDITOR', 'REVISOR', 'DESARROLLO')")
    public ResponseEntity<List<BitacoraProyectoResponse>> list(@PathVariable Long idProyecto) {
        return ResponseEntity.ok(manageProjectLogsUseCase.list(idProyecto));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR', 'AUDITOR', 'REVISOR', 'DESARROLLO')")
    public ResponseEntity<BitacoraProyectoResponse> create(
            @PathVariable Long idProyecto, @Valid @RequestBody BitacoraProyectoRequest request) {
        return ResponseEntity.status(201).body(manageProjectLogsUseCase.create(idProyecto, request));
    }

    @PatchMapping("/estado")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR', 'AUDITOR')")
    public ResponseEntity<Void> updateProjectState(
            @PathVariable Long idProyecto, @Valid @RequestBody EstadoProyectoRequest request) {
        manageProjectLogsUseCase.updateProjectState(idProyecto, request);
        return ResponseEntity.noContent().build();
    }
}
