package com.oghs.sgdsws.dto.request;

import jakarta.validation.constraints.NotNull;

public record EstadoProyectoRequest(@NotNull Long idEstadoProyecto) {
}
