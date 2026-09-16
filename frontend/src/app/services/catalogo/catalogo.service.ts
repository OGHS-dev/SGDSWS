import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ambiente } from '../../../environments/environment';
import { Catalogo } from '../Catalogo';

@Injectable({ providedIn: 'root' })
export class CatalogoService {
  constructor(private readonly httpClient: HttpClient) {}

  listar(tipo: string): Observable<Catalogo[]> {
    return this.httpClient.get<Catalogo[]>(`${ambiente.urlApi}/catalogos/${tipo}`);
  }

  listarEstadosProyecto(): Observable<Catalogo[]> {
    return this.listar('estados-proyecto');
  }

  listarCategorias(): Observable<Catalogo[]> {
    return this.listar('categorias');
  }

  listarPrioridades(): Observable<Catalogo[]> {
    return this.listar('prioridades');
  }

  listarModulos(): Observable<Catalogo[]> {
    return this.listar('modulos');
  }

  listarHallazgos(): Observable<Catalogo[]> {
    return this.listar('hallazgos');
  }

  listarIncidentes(): Observable<Catalogo[]> {
    return this.listar('incidentes');
  }

  listarNivelesRiesgo(): Observable<Catalogo[]> {
    return this.listar('niveles-riesgo');
  }

  listarImpactos(): Observable<Catalogo[]> {
    return this.listar('impactos');
  }

  listarEstadosBitacoraProyecto(): Observable<Catalogo[]> {
    return this.listar('estados-bitacora-proyecto');
  }
}
