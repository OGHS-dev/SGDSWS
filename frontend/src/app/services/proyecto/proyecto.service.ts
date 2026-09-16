import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ambiente } from '../../../environments/environment';
import { Proyecto } from '../Proyecto';

@Injectable({ providedIn: 'root' })
export class ProyectoService {
  constructor(private readonly httpClient: HttpClient) {}

  listarProyectos(): Observable<Proyecto[]> {
    return this.httpClient.get<Proyecto[]>(`${ambiente.urlApi}/proyectos`);
  }

  buscarProyecto(idProyecto: number): Observable<Proyecto> {
    return this.httpClient.get<Proyecto>(`${ambiente.urlApi}/proyectos/${idProyecto}`);
  }

  crearProyecto(proyecto: Proyecto): Observable<Proyecto> {
    return this.httpClient.post<Proyecto>(`${ambiente.urlApi}/proyectos`, proyecto);
  }

  actualizarProyecto(idProyecto: number, proyecto: Proyecto): Observable<Proyecto> {
    return this.httpClient.put<Proyecto>(`${ambiente.urlApi}/proyectos/${idProyecto}`, proyecto);
  }

  eliminarProyecto(idProyecto: number): Observable<void> {
    return this.httpClient.delete<void>(`${ambiente.urlApi}/proyectos/${idProyecto}`);
  }
}
