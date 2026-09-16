package com.oghs.sgdsws.application.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.oghs.sgdsws.application.port.in.ManageProjectResourcesUseCase;
import com.oghs.sgdsws.application.port.out.CurrentUserPort;
import com.oghs.sgdsws.application.port.out.ProjectLogPersistencePort;
import com.oghs.sgdsws.application.port.out.ProjectResourcePersistencePort;
import com.oghs.sgdsws.dto.request.ComentarioRequest;
import com.oghs.sgdsws.dto.response.ArchivoResponse;
import com.oghs.sgdsws.dto.response.ComentarioResponse;
import com.oghs.sgdsws.exceptionhandler.ResourceNotFoundException;
import com.oghs.sgdsws.model.entity.Archivo;
import com.oghs.sgdsws.model.entity.BitacoraProyecto;
import com.oghs.sgdsws.model.entity.Comentario;
import com.oghs.sgdsws.model.entity.Proyecto;
import com.oghs.sgdsws.repository.ProyectoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectResourceService implements ManageProjectResourcesUseCase {
    private final ProjectResourcePersistencePort resourcePersistencePort;
    private final ProjectLogPersistencePort logPersistencePort;
    private final ProyectoRepository projectRepository;
    private final CurrentUserPort currentUserPort;

    @Override
    @Transactional(readOnly = true)
    public List<ArchivoResponse> listFiles(Long idBitacoraProyecto) {
        BitacoraProyecto log = findLog(idBitacoraProyecto);
        return resourcePersistencePort.findFiles(log).stream().map(this::toFileResponse).toList();
    }

    @Override
    @Transactional
    public ArchivoResponse uploadFile(Long idBitacoraProyecto, MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("El archivo no puede estar vacío.");
        }
        BitacoraProyecto log = findLog(idBitacoraProyecto);
        try {
            Archivo archivo = new Archivo();
            archivo.setBitacoraProyecto(log);
            archivo.setNombreArchivo(file.getOriginalFilename() == null ? "archivo" : file.getOriginalFilename());
            archivo.setTamanoArchivo(file.getSize());
            archivo.setArchivo(file.getBytes());
            archivo.setUsuarioCreacion(currentUserPort.username());
            return toFileResponse(resourcePersistencePort.saveFile(archivo));
        } catch (IOException exception) {
            throw new IllegalStateException("No fue posible leer el archivo.", exception);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public byte[] downloadFile(Long idArchivo) {
        return findFile(idArchivo).getArchivo();
    }

    @Override
    @Transactional(readOnly = true)
    public String fileName(Long idArchivo) {
        return findFile(idArchivo).getNombreArchivo();
    }

    @Override
    @Transactional
    public void deleteFile(Long idArchivo) {
        resourcePersistencePort.deleteFile(findFile(idArchivo));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ComentarioResponse> listComments(Long idBitacoraProyecto) {
        BitacoraProyecto log = findLog(idBitacoraProyecto);
        return resourcePersistencePort.findComments(log).stream().map(this::toCommentResponse).toList();
    }

    @Override
    @Transactional
    public ComentarioResponse addComment(Long idBitacoraProyecto, ComentarioRequest request) {
        Comentario comentario = new Comentario();
        comentario.setBitacoraProyecto(findLog(idBitacoraProyecto));
        comentario.setComentario(request.comentario());
        comentario.setUsuarioCreacion(currentUserPort.username());
        return toCommentResponse(resourcePersistencePort.saveComment(comentario));
    }

    @Override
    @Transactional(readOnly = true)
    public byte[] generateReport(Long idProyecto) {
        Proyecto project = projectRepository.findById(idProyecto)
                .orElseThrow(() -> new ResourceNotFoundException("Proyecto no encontrado: " + idProyecto));
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("REPORTE EVENTOS PROYECTO");
            String[] headers = {"ID", "PROYECTO", "REVISIÓN", "FECHA", "ESTADO", "DESCRIPCIÓN",
                    "COMPONENTE", "VERSIÓN", "FRECUENCIA", "ACCIONES", "USUARIO ASIGNADO"};
            writeRow(sheet.createRow(0), headers);
            int rowNumber = 1;
            for (BitacoraProyecto log : logPersistencePort.findByProject(project)) {
                writeRow(sheet.createRow(rowNumber++), log.getIdBitacoraProyecto(), project.getNombre(),
                        log.getRevision(), log.getFechaBitacora(), log.getEstadoBitacoraProyecto() == null
                                ? "" : log.getEstadoBitacoraProyecto().getDescripcion(),
                        log.getDescripcion(), log.getComponente(), log.getVersion(), log.getFrecuencia(),
                        log.getAcciones(), log.getUsuarioAsignado());
            }
            workbook.write(output);
            return output.toByteArray();
        } catch (IOException exception) {
            throw new IllegalStateException("No fue posible generar el reporte.", exception);
        }
    }

    private BitacoraProyecto findLog(Long id) {
        return logPersistencePort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bitácora no encontrada: " + id));
    }

    private Archivo findFile(Long id) {
        return resourcePersistencePort.findFile(id)
                .orElseThrow(() -> new ResourceNotFoundException("Archivo no encontrado: " + id));
    }

    private ArchivoResponse toFileResponse(Archivo file) {
        return new ArchivoResponse(file.getIdArchivo(), file.getBitacoraProyecto().getIdBitacoraProyecto(),
                file.getNombreArchivo(), file.getTamanoArchivo(), toLocalDateTime(file.getFechaCreacion()),
                file.getUsuarioCreacion());
    }

    private ComentarioResponse toCommentResponse(Comentario comment) {
        return new ComentarioResponse(comment.getIdComentario(), comment.getBitacoraProyecto().getIdBitacoraProyecto(),
                comment.getComentario(), toLocalDateTime(comment.getFechaCreacion()), comment.getUsuarioCreacion());
    }

    private java.time.LocalDateTime toLocalDateTime(Date date) {
        return date == null ? null : date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    private void writeRow(Row row, Object... values) {
        for (int index = 0; index < values.length; index++) {
            Object value = values[index];
            row.createCell(index).setCellValue(value == null ? "" : String.valueOf(value));
        }
    }
}
