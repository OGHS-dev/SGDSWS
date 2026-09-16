package com.oghs.sgdsws.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BitacoraProyectoRequest(
        @NotBlank @Size(max = 500) String descripcion,
        String componente,
        String version,
        String frecuencia,
        Long idModulo,
        Long idHallazgo,
        Long idIncidente,
        Long idCategoria,
        Long idPrioridad,
        Long idImpacto,
        Long idNivelRiesgo,
        Long idEstadoBitacoraProyecto,
        String acciones,
        String usuarioAsignado) {
}
