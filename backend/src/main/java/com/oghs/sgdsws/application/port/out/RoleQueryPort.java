package com.oghs.sgdsws.application.port.out;

import java.util.List;

import com.oghs.sgdsws.model.entity.Rol;

public interface RoleQueryPort {
    List<Rol> findActiveRoles();
}
