import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ambiente } from '../../../environments/environment';
import { BitacoraProyecto } from '../BitacoraProyecto';

@Injectable({ providedIn: 'root' })
export class BitacoraService {
  constructor(private readonly httpClient: HttpClient) {}

  listar(idProyecto: number): Observable<BitacoraProyecto[]> {
    return this.httpClient.get<BitacoraProyecto[]>(`${ambiente.urlApi}/proyectos/${idProyecto}/bitacora`);
  }

  crear(idProyecto: number, bitacora: BitacoraProyecto): Observable<BitacoraProyecto> {
    return this.httpClient.post<BitacoraProyecto>(
      `${ambiente.urlApi}/proyectos/${idProyecto}/bitacora`, bitacora);
  }

  actualizarEstadoProyecto(idProyecto: number, idEstadoProyecto: number): Observable<void> {
    return this.httpClient.patch<void>(
      `${ambiente.urlApi}/proyectos/${idProyecto}/bitacora/estado`, { idEstadoProyecto });
  }
}
