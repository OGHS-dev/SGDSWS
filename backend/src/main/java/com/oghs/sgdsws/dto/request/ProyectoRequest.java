package com.oghs.sgdsws.dto.request;

import java.time.LocalDate;
import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProyectoRequest(
        @NotBlank @Size(max = 100) String nombre,
        @NotBlank @Size(max = 200) String descripcion,
        @NotNull Long idEstadoProyecto,
        @NotBlank String responsable,
        @NotNull LocalDate fechaInicio,
        @NotNull LocalDate fechaFin,
        Set<Long> idsUsuarios) {
}
