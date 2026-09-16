import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AutenticacionService } from './autenticacion/autenticacion.service';

export const authGuard: CanActivateFn = () => {
  const autenticacionService = inject(AutenticacionService);
  const router = inject(Router);

  return autenticacionService.existeTokenLocalStorage()
    ? true
    : router.createUrlTree(['/inicioSesion']);
};
