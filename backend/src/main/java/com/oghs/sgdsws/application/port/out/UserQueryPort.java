package com.oghs.sgdsws.application.port.out;

import java.util.List;

import com.oghs.sgdsws.model.entity.Usuario;

public interface UserQueryPort {
    List<Usuario> findActiveUsers();
}
