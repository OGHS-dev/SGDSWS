package com.oghs.sgdsws.application.port.in;

import java.util.List;

import com.oghs.sgdsws.dto.request.BitacoraProyectoRequest;
import com.oghs.sgdsws.dto.request.EstadoProyectoRequest;
import com.oghs.sgdsws.dto.response.BitacoraProyectoResponse;

public interface ManageProjectLogsUseCase {
    List<BitacoraProyectoResponse> list(Long idProyecto);
    BitacoraProyectoResponse create(Long idProyecto, BitacoraProyectoRequest request);
    void updateProjectState(Long idProyecto, EstadoProyectoRequest request);
}
