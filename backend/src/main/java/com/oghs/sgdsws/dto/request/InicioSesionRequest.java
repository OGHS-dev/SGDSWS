package com.oghs.sgdsws.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InicioSesionRequest {
    @NotBlank
    private String nombreUsuario;

    @NotBlank
    private String contrasena;
}
