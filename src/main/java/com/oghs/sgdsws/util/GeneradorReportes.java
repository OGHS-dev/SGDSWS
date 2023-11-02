package com.oghs.sgdsws.util;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.oghs.sgdsws.model.entity.BitacoraProyecto;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

public class GeneradorReportes {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<BitacoraProyecto> listaBitacoraProyecto;

    public GeneradorReportes(List<BitacoraProyecto> listaBitacoraProyecto) {
        workbook = new XSSFWorkbook();
        this.listaBitacoraProyecto = listaBitacoraProyecto;
    }

    private void generarEncabezado() {
        sheet = workbook.createSheet("REPORTE EVENTOS PROYECTO");
        Row row = sheet.createRow(0);
        CellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        cellStyle.setFont(font);
        cellStyle.setFillForegroundColor(IndexedColors.BLACK.getIndex());
        cellStyle.setFillBackgroundColor(IndexedColors.WHITE.getIndex());

        List<String> listaEncabezados = List.of("ID", "PROYECTO", "MÓDULO", "HALLAZGO", "INCIDENTE", "CATEGORÍA", "PRIORIDAD", "IMPACTO", "NIVEL DE RIESGO", "ESTADO DE EVENTO", "FECHA BITÁCORA", "USUARIO DE REPORTE", "REVISIÓN", "DESCRIPCIÓN", "COMPONENTE", "VERSIÓN", "FRECUENCIA", "ACCIONES", "USUARIO ASIGNADO", "FECHA DE ANTENCIÓN");
        
        for (int i = 0; i < listaEncabezados.size(); i++) {
            generarCelda(row, i, listaEncabezados.get(i), cellStyle);
        }
    }

    private void generarCelda(Row row, int contColumna, Object valorCelda, CellStyle cellStyle) {
        sheet.autoSizeColumn(contColumna);
        Cell cell = row.createCell(contColumna);

        if (valorCelda instanceof Integer) {
            cell.setCellValue((Integer) valorCelda);
        } else if (valorCelda instanceof Long) {
            cell.setCellValue((Long) valorCelda);
        } else if (valorCelda instanceof String) {
            cell.setCellValue((String) valorCelda);
        } else if (valorCelda instanceof Date) {
            // cellStyle.setDataFormat((short) 14);
            cell.setCellValue((Date) valorCelda);
        } else {
            cell.setCellValue((Boolean) valorCelda);
        }
        cell.setCellStyle(cellStyle);
    }

    private void escribir() {
        int contFila = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (BitacoraProyecto bitacoraProyecto : this.listaBitacoraProyecto) {
            Row row = sheet.createRow(contFila ++);
            int contColumna = 0;
            generarCelda(row, contColumna ++, !Objects.isNull(bitacoraProyecto.getIdBitacoraProyecto()) ? bitacoraProyecto.getIdBitacoraProyecto() : "", style);
            generarCelda(row, contColumna ++, !Objects.isNull(bitacoraProyecto.getProyecto()) ? bitacoraProyecto.getProyecto().getDescripcion() : "", style);
            generarCelda(row, contColumna ++, !Objects.isNull(bitacoraProyecto.getModulo()) ? bitacoraProyecto.getModulo().getDescripcion() : "", style);
            generarCelda(row, contColumna ++, !Objects.isNull(bitacoraProyecto.getHallazgo()) ? bitacoraProyecto.getHallazgo().getDescripcion() : "", style);
            generarCelda(row, contColumna ++, !Objects.isNull(bitacoraProyecto.getIncidente()) ? bitacoraProyecto.getIncidente().getDescripcion() : "", style);
            generarCelda(row, contColumna ++, !Objects.isNull(bitacoraProyecto.getCategoria()) ? bitacoraProyecto.getCategoria().getDescripcion() : "", style);
            generarCelda(row, contColumna ++, !Objects.isNull(bitacoraProyecto.getPrioridad()) ? bitacoraProyecto.getPrioridad().getDescripcion() : "", style);
            generarCelda(row, contColumna ++, !Objects.isNull(bitacoraProyecto.getImpacto()) ? bitacoraProyecto.getImpacto().getDescripcion() : "", style);
            generarCelda(row, contColumna ++, !Objects.isNull(bitacoraProyecto.getNivelRiesgo()) ? bitacoraProyecto.getNivelRiesgo().getDescripcion() : "", style);
            generarCelda(row, contColumna ++, !Objects.isNull(bitacoraProyecto.getEstadoBitacoraProyecto()) ? bitacoraProyecto.getEstadoBitacoraProyecto().getDescripcion() : "", style);
            generarCelda(row, contColumna ++, !Objects.isNull(bitacoraProyecto.getFechaBitacora()) ? bitacoraProyecto.getFechaBitacora() : "", style);
            generarCelda(row, contColumna ++, !Objects.isNull(bitacoraProyecto.getUsuarioReporte()) ? bitacoraProyecto.getUsuarioReporte() : "", style);
            generarCelda(row, contColumna ++, !Objects.isNull(bitacoraProyecto.getRevision()) ? bitacoraProyecto.getRevision() : "", style);
            generarCelda(row, contColumna ++, !Objects.isNull(bitacoraProyecto.getDescripcion()) ? bitacoraProyecto.getDescripcion() : "", style);
            generarCelda(row, contColumna ++, !Objects.isNull(bitacoraProyecto.getComponente()) ? bitacoraProyecto.getComponente() : "", style);
            generarCelda(row, contColumna ++, !Objects.isNull(bitacoraProyecto.getVersion()) ? bitacoraProyecto.getVersion() : "", style);
            generarCelda(row, contColumna ++, !Objects.isNull(bitacoraProyecto.getFrecuencia()) ? bitacoraProyecto.getFrecuencia() : "", style);
            generarCelda(row, contColumna ++, !Objects.isNull(bitacoraProyecto.getAcciones()) ? bitacoraProyecto.getAcciones() : "", style);
            generarCelda(row, contColumna ++, !Objects.isNull(bitacoraProyecto.getUsuarioAsignado()) ? bitacoraProyecto.getUsuarioAsignado() : "", style);
            generarCelda(row, contColumna, !Objects.isNull(bitacoraProyecto.getFechaAtencion()) ? bitacoraProyecto.getFechaAtencion() : "", style);
        }
    }

    public void generarArchivoExcel(HttpServletResponse response) throws IOException {
        generarEncabezado();
        escribir();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
    
}
