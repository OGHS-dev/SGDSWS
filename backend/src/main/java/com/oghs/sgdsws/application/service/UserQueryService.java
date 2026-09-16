package com.oghs.sgdsws.application.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.oghs.sgdsws.application.port.in.ListUsersUseCase;
import com.oghs.sgdsws.application.port.out.UserQueryPort;
import com.oghs.sgdsws.dto.response.UsuarioResponse;
import com.oghs.sgdsws.model.entity.Rol;
import com.oghs.sgdsws.model.entity.Usuario;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserQueryService implements ListUsersUseCase {
    private final UserQueryPort userQueryPort;

    @Override
    public List<UsuarioResponse> listActiveUsers() {
        return userQueryPort.findActiveUsers().stream().map(this::toResponse).toList();
    }

    private UsuarioResponse toResponse(Usuario usuario) {
        return UsuarioResponse.builder()
                .idUsuario(usuario.getIdUsuario())
                .nombreUsuario(usuario.getNombreUsuario())
                .correo(usuario.getCorreo())
                .roles(usuario.getRoles().stream().map(Rol::getCodigo).collect(Collectors.toSet()))
                .build();
    }
}
