package com.oghs.sgdsws.controller;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.oghs.sgdsws.application.port.in.ManageProjectResourcesUseCase;
import com.oghs.sgdsws.dto.request.ComentarioRequest;
import com.oghs.sgdsws.dto.response.ArchivoResponse;
import com.oghs.sgdsws.dto.response.ComentarioResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProjectResourceController {
    private final ManageProjectResourcesUseCase resourcesUseCase;

    @GetMapping("/bitacora/{idBitacoraProyecto}/archivos")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR', 'AUDITOR', 'REVISOR', 'DESARROLLO')")
    public ResponseEntity<List<ArchivoResponse>> listFiles(@PathVariable Long idBitacoraProyecto) {
        return ResponseEntity.ok(resourcesUseCase.listFiles(idBitacoraProyecto));
    }

    @PostMapping(value = "/bitacora/{idBitacoraProyecto}/archivos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR', 'AUDITOR', 'REVISOR', 'DESARROLLO')")
    public ResponseEntity<ArchivoResponse> uploadFile(
            @PathVariable Long idBitacoraProyecto, @RequestPart("file") MultipartFile file) {
        return ResponseEntity.status(201).body(resourcesUseCase.uploadFile(idBitacoraProyecto, file));
    }

    @GetMapping("/archivos/{idArchivo}/descarga")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR', 'AUDITOR', 'REVISOR', 'DESARROLLO')")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Long idArchivo) {
        String fileName = resourcesUseCase.fileName(idArchivo);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment().filename(fileName).build().toString())
                .body(new ByteArrayResource(resourcesUseCase.downloadFile(idArchivo)));
    }

    @DeleteMapping("/archivos/{idArchivo}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR', 'AUDITOR', 'REVISOR', 'DESARROLLO')")
    public ResponseEntity<Void> deleteFile(@PathVariable Long idArchivo) {
        resourcesUseCase.deleteFile(idArchivo);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/bitacora/{idBitacoraProyecto}/comentarios")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR', 'AUDITOR', 'REVISOR', 'DESARROLLO')")
    public ResponseEntity<List<ComentarioResponse>> listComments(@PathVariable Long idBitacoraProyecto) {
        return ResponseEntity.ok(resourcesUseCase.listComments(idBitacoraProyecto));
    }

    @PostMapping("/bitacora/{idBitacoraProyecto}/comentarios")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR', 'AUDITOR', 'REVISOR', 'DESARROLLO')")
    public ResponseEntity<ComentarioResponse> addComment(
            @PathVariable Long idBitacoraProyecto, @Valid @RequestBody ComentarioRequest request) {
        return ResponseEntity.status(201).body(resourcesUseCase.addComment(idBitacoraProyecto, request));
    }

    @GetMapping("/proyectos/{idProyecto}/reporte")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR', 'AUDITOR', 'REVISOR', 'DESARROLLO')")
    public ResponseEntity<ByteArrayResource> report(@PathVariable Long idProyecto) {
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment().filename("reporte-proyecto-" + idProyecto + ".xlsx").build().toString())
                .body(new ByteArrayResource(resourcesUseCase.generateReport(idProyecto)));
    }
}
