package com.oghs.sgdsws.dto.response;

import java.time.LocalDateTime;

public record ArchivoResponse(
        Long idArchivo,
        Long idBitacoraProyecto,
        String nombreArchivo,
        Long tamanoArchivo,
        LocalDateTime fechaCreacion,
        String usuarioCreacion) {
}
