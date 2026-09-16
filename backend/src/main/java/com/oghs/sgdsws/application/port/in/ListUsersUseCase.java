package com.oghs.sgdsws.application.port.in;

import java.util.List;

import com.oghs.sgdsws.dto.response.UsuarioResponse;

public interface ListUsersUseCase {
    List<UsuarioResponse> listActiveUsers();
}
