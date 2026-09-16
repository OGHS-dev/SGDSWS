package com.oghs.sgdsws.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ComentarioRequest(@NotBlank @Size(max = 2000) String comentario) {
}
