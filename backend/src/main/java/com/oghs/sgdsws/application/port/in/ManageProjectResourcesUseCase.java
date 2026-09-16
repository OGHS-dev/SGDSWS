package com.oghs.sgdsws.application.port.in;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.oghs.sgdsws.dto.request.ComentarioRequest;
import com.oghs.sgdsws.dto.response.ArchivoResponse;
import com.oghs.sgdsws.dto.response.ComentarioResponse;

public interface ManageProjectResourcesUseCase {
    List<ArchivoResponse> listFiles(Long idBitacoraProyecto);
    ArchivoResponse uploadFile(Long idBitacoraProyecto, MultipartFile file);
    byte[] downloadFile(Long idArchivo);
    String fileName(Long idArchivo);
    void deleteFile(Long idArchivo);
    List<ComentarioResponse> listComments(Long idBitacoraProyecto);
    ComentarioResponse addComment(Long idBitacoraProyecto, ComentarioRequest request);
    byte[] generateReport(Long idProyecto);
}
