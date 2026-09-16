package com.oghs.sgdsws.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oghs.sgdsws.application.port.in.ListRolesUseCase;
import com.oghs.sgdsws.dto.response.RolResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RolController {
    private final ListRolesUseCase listRolesUseCase;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR', 'AUDITOR', 'REVISOR')")
    public ResponseEntity<List<RolResponse>> listarRoles() {
        return ResponseEntity.ok(listRolesUseCase.listActiveRoles());
    }
}
