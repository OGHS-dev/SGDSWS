import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ambiente } from '../../../environments/environment';
import { Archivo } from '../Archivo';
import { Comentario } from '../Comentario';

@Injectable({ providedIn: 'root' })
export class RecursoProyectoService {
  constructor(private readonly httpClient: HttpClient) {}

  listarArchivos(idBitacoraProyecto: number): Observable<Archivo[]> {
    return this.httpClient.get<Archivo[]>(`${ambiente.urlApi}/bitacora/${idBitacoraProyecto}/archivos`);
  }

  subirArchivo(idBitacoraProyecto: number, file: File): Observable<Archivo> {
    const formData = new FormData();
    formData.append('file', file);
    return this.httpClient.post<Archivo>(
      `${ambiente.urlApi}/bitacora/${idBitacoraProyecto}/archivos`, formData);
  }

  descargarArchivo(idArchivo: number): Observable<Blob> {
    return this.httpClient.get(`${ambiente.urlApi}/archivos/${idArchivo}/descarga`, {
      responseType: 'blob'
    });
  }

  eliminarArchivo(idArchivo: number): Observable<void> {
    return this.httpClient.delete<void>(`${ambiente.urlApi}/archivos/${idArchivo}`);
  }

  listarComentarios(idBitacoraProyecto: number): Observable<Comentario[]> {
    return this.httpClient.get<Comentario[]>(
      `${ambiente.urlApi}/bitacora/${idBitacoraProyecto}/comentarios`);
  }

  agregarComentario(idBitacoraProyecto: number, comentario: string): Observable<Comentario> {
    return this.httpClient.post<Comentario>(
      `${ambiente.urlApi}/bitacora/${idBitacoraProyecto}/comentarios`, { comentario });
  }

  descargarReporte(idProyecto: number): Observable<Blob> {
    return this.httpClient.get(`${ambiente.urlApi}/proyectos/${idProyecto}/reporte`, {
      responseType: 'blob'
    });
  }
}
