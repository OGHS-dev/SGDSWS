package com.oghs.sgdsws.controller;

import java.util.List;
import java.util.Locale;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oghs.sgdsws.application.port.in.CatalogType;
import com.oghs.sgdsws.application.port.in.ListCataloguesUseCase;
import com.oghs.sgdsws.dto.response.CatalogoResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/catalogos")
@RequiredArgsConstructor
public class CatalogueController {
    private final ListCataloguesUseCase listCataloguesUseCase;

    @GetMapping("/{type}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR', 'AUDITOR', 'REVISOR', 'DESARROLLO')")
    public ResponseEntity<List<CatalogoResponse>> list(@PathVariable String type) {
        return ResponseEntity.ok(listCataloguesUseCase.list(parseType(type)));
    }

    private CatalogType parseType(String type) {
        return switch (type.toLowerCase(Locale.ROOT)) {
            case "estados-proyecto" -> CatalogType.ESTADOS_PROYECTO;
            case "categorias" -> CatalogType.CATEGORIAS;
            case "prioridades" -> CatalogType.PRIORIDADES;
            case "modulos" -> CatalogType.MODULOS;
            case "hallazgos" -> CatalogType.HALLAZGOS;
            case "incidentes" -> CatalogType.INCIDENTES;
            case "niveles-riesgo" -> CatalogType.NIVELES_RIESGO;
            case "impactos" -> CatalogType.IMPACTOS;
            case "estados-bitacora-proyecto" -> CatalogType.ESTADOS_BITACORA_PROYECTO;
            default -> throw new IllegalArgumentException("Catálogo no soportado: " + type);
        };
    }
}
