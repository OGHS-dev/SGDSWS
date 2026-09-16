package com.oghs.sgdsws.dto.response;

import java.time.LocalDateTime;

public record BitacoraProyectoResponse(
        Long idBitacoraProyecto,
        Long idProyecto,
        LocalDateTime fechaBitacora,
        String usuarioReporte,
        Long revision,
        String descripcion,
        String componente,
        String version,
        String frecuencia,
        CatalogoResponse modulo,
        CatalogoResponse hallazgo,
        CatalogoResponse incidente,
        CatalogoResponse categoria,
        CatalogoResponse prioridad,
        CatalogoResponse impacto,
        CatalogoResponse nivelRiesgo,
        CatalogoResponse estadoBitacoraProyecto,
        String acciones,
        String usuarioAsignado) {
}
