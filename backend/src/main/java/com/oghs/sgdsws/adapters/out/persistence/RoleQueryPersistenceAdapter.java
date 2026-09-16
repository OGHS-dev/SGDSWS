package com.oghs.sgdsws.adapters.out.persistence;

import java.util.List;

import org.springframework.stereotype.Component;

import com.oghs.sgdsws.application.port.out.RoleQueryPort;
import com.oghs.sgdsws.model.Estatus;
import com.oghs.sgdsws.model.entity.Rol;
import com.oghs.sgdsws.repository.RolRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RoleQueryPersistenceAdapter implements RoleQueryPort {
    private final RolRepository rolRepository;

    @Override
    public List<Rol> findActiveRoles() {
        return rolRepository.findAllByEstatusOrderByDescripcionAsc(Estatus.ACTIVO);
    }
}
