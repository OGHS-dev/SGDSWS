package com.oghs.sgdsws.application.port.in;

import java.util.List;

import com.oghs.sgdsws.dto.response.RolResponse;

public interface ListRolesUseCase {
    List<RolResponse> listActiveRoles();
}
