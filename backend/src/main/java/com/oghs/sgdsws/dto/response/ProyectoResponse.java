package com.oghs.sgdsws.dto.response;

import java.time.LocalDate;
import java.util.Set;

public record ProyectoResponse(
        Long idProyecto,
        String nombre,
        String descripcion,
        Long idEstadoProyecto,
        String estadoProyecto,
        String responsable,
        LocalDate fechaInicio,
        LocalDate fechaFin,
        Set<Long> idsUsuarios) {
}
