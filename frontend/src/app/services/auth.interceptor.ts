import { HttpErrorResponse, HttpEvent, HttpHandlerFn, HttpInterceptorFn, HttpRequest } from '@angular/common/http';
import { inject } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { AutenticacionService } from './autenticacion/autenticacion.service';
import { Router } from '@angular/router';

export const authInterceptor: HttpInterceptorFn = (req: HttpRequest<any>, next: HttpHandlerFn): Observable<HttpEvent<any>> => {
    const router = inject(Router);
    const autenticacionService = inject(AutenticacionService);
    const token = autenticacionService.obtenerTokenLocalStorage();

    if (token) {
        req = req.clone({
            headers: req.headers.set('Authorization', `Bearer ${token}`)
        });
    }
    
    return next(req).pipe(
        catchError((error) => {
            if (error instanceof HttpErrorResponse && error.status === 401) {
                autenticacionService.cerrarSesion();
                router.navigate(['inicioSesion']);
            }
            return throwError(() => error);
        })
    );
};
