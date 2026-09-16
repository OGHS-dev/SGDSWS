import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';

import { Router, RouterLink, RouterOutlet } from '@angular/router';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatMenuModule } from '@angular/material/menu';
import { MatListModule } from '@angular/material/list';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { Usuario } from '../../services/Usuario';
import { AutenticacionService } from '../../services/autenticacion/autenticacion.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-nav',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink, MatSidenavModule, MatToolbarModule, MatMenuModule, MatListModule, MatButtonModule, MatIconModule],
  templateUrl: './nav.component.html',
  styleUrl: './nav.component.css'
})
export class NavComponent implements OnInit {
  usuarioEnSesion: boolean = false;
  usuario: Usuario | null = null;

  constructor(private readonly snackBar: MatSnackBar, private readonly router: Router, private readonly autenticacionService: AutenticacionService) { }

  ngOnInit(): void {
    this.usuarioEnSesion = this.autenticacionService.existeTokenLocalStorage();
    this.usuario = this.autenticacionService.obtenerUsuarioLocalStorage();

    this.autenticacionService.sesionEstatusSubject.asObservable().subscribe({
      next: () => {
        this.usuarioEnSesion = this.autenticacionService.existeTokenLocalStorage();
        this.usuario = this.autenticacionService.obtenerUsuarioLocalStorage();
      }
    });
  }

  cerrarSesion() {
    this.autenticacionService.cerrarSesion();
    // this.router.navigate(['inicioSesion']);
    window.location.reload();

    this.snackBar.open('Sesión finalizada con éxito.', 'Aceptar', {
      duration: 5000,
      verticalPosition: 'top',
      horizontalPosition: 'right',
      panelClass: ['success-snackbar']
    });
  }

  tieneRol(...roles: string[]): boolean {
    const rolesUsuario = this.usuario?.roles ?? [];
    return roles.some(role => rolesUsuario.includes(role) || rolesUsuario.includes(`ROLE_${role}`));
  }

}
