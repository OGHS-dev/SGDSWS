import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Usuario } from '../Usuario';
import { Rol } from '../Rol';
import { ambiente } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  constructor(private readonly httpClient: HttpClient) { }

  listarUsuarios(): Observable<Usuario[]> {
    return this.httpClient.get<Usuario[]>(`${ambiente.urlApi}/usuarios`);
  }

  buscarUsuario(idUsuario: number): Observable<Usuario> {
    return this.httpClient.get<Usuario>(`${ambiente.urlApi}/usuarios/buscar/${idUsuario}`);
  }

  listarRoles(): Observable<Rol[]> {
    return this.httpClient.get<Rol[]>(`${ambiente.urlApi}/roles`);
  }
}
