package com.oghs.sgdsws.adapters.out.persistence;

import java.util.List;

import org.springframework.stereotype.Component;

import com.oghs.sgdsws.application.port.out.UserQueryPort;
import com.oghs.sgdsws.model.entity.Usuario;
import com.oghs.sgdsws.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserQueryPersistenceAdapter implements UserQueryPort {
    private final UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> findActiveUsers() {
        return usuarioRepository.findAllByEstatusOrderByNombreUsuarioAsc(
                com.oghs.sgdsws.model.Estatus.ACTIVO);
    }
}
