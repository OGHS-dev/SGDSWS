package com.oghs.sgdsws.dto.response;

import java.time.LocalDateTime;

public record ComentarioResponse(
        Long idComentario,
        Long idBitacoraProyecto,
        String comentario,
        LocalDateTime fechaCreacion,
        String usuarioCreacion) {
}
