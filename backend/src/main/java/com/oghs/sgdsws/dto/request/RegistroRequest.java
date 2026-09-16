package com.oghs.sgdsws.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistroRequest {
    @NotBlank
    @Size(max = 20)
    private String nombreUsuario;

    @NotBlank
    @Size(min = 8, max = 100)
    private String contrasena;

    @NotBlank
    @Email
    private String correo;
}
