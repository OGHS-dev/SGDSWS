import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Usuario } from '../Usuario';
import { ambiente } from '../../../environments/environment';
import { Observable, Subject } from 'rxjs';
import { Token } from '../Token';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AutenticacionService {
  public sesionEstatusSubject = new Subject<boolean>();

  constructor(private readonly httpClient: HttpClient) { }

  registro(usuario: Usuario): Observable<Usuario> {
    return this.httpClient.post<Usuario>(ambiente.urlApi + "/auth/registro", usuario, httpOptions);
  }

  inicioSesion(usuario: Usuario): Observable<Token> {
    return this.httpClient.post<Token>(ambiente.urlApi + "/auth/inicioSesion", usuario, httpOptions);
  }

  usuarioEnSesion(): Observable<Usuario> {
    return this.httpClient.get<Usuario>(ambiente.urlApi + "/auth/usuarioEnSesion");
  }

  public guardarTokenLocalStorage(token: string): void {
    if (this.isBrowser()) {
      sessionStorage.setItem('token', token);
    }
  }

  public obtenerTokenLocalStorage(): string | null {
    return this.isBrowser() ? sessionStorage.getItem('token') : null;
  }

  public guardarUsuarioLocalStorage(usuario: Usuario): void {
    if (this.isBrowser()) {
      sessionStorage.setItem('usuario', JSON.stringify(usuario));
    }
  }

  public obtenerUsuarioLocalStorage(): Usuario | null {
    if (this.isBrowser()) {
      let usuario = sessionStorage.getItem('usuario');
      if (usuario != null) {
        return JSON.parse(usuario);
      } else {
        return null;
      }
    }
    return null;
  }

  public existeTokenLocalStorage(): boolean {
    if (this.isBrowser()) {
      let token = sessionStorage.getItem('token');
      return !(token == undefined || token == null || token == '');
    }
    return false;
  }

  cerrarSesion(): boolean {
    if (this.isBrowser()) {
      sessionStorage.removeItem('token');
      sessionStorage.removeItem('usuario');
    }
    this.sesionEstatusSubject.next(false);
    return true;
  }

  private isBrowser(): boolean {
    return typeof sessionStorage !== 'undefined';
  }

  // obtenerUsuario(idUsuario: number) : Observable<Usuario> {
  //   return this.httpClient.get<Usuario>(ambiente.urlApi + "usuario/" + idUsuario).pipe(catchError(this.manejoErrores));
  // }

  // private manejoErrores(error: HttpErrorResponse) {
  //   if (error.status === 0) {
  //     console.error('Se ha producido un error: ', error.error);
  //   } else {
  //     console.log('El backend retorno el siguiente codigo de estado: ', error.status, error.error);
  //   }
  //   return throwError(() => new Error('Error'));
  // }
}
