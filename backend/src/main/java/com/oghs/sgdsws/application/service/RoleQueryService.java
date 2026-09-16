package com.oghs.sgdsws.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oghs.sgdsws.application.port.in.ListRolesUseCase;
import com.oghs.sgdsws.application.port.out.RoleQueryPort;
import com.oghs.sgdsws.dto.response.RolResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleQueryService implements ListRolesUseCase {
    private final RoleQueryPort roleQueryPort;

    @Override
    public List<RolResponse> listActiveRoles() {
        return roleQueryPort.findActiveRoles().stream()
                .map(role -> new RolResponse(role.getIdRol(), role.getCodigo(), role.getDescripcion()))
                .toList();
    }
}
