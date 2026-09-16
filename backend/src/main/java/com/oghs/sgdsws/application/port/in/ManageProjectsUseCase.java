package com.oghs.sgdsws.application.port.in;

import java.util.List;

import com.oghs.sgdsws.dto.request.ProyectoRequest;
import com.oghs.sgdsws.dto.response.ProyectoResponse;

public interface ManageProjectsUseCase {
    List<ProyectoResponse> listProjects();
    ProyectoResponse findProject(Long idProyecto);
    ProyectoResponse createProject(ProyectoRequest request);
    ProyectoResponse updateProject(Long idProyecto, ProyectoRequest request);
    void deleteProject(Long idProyecto);
}
