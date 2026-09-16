import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { AbstractControl, FormBuilder, ReactiveFormsModule, Validators, FormGroup } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatIconModule } from '@angular/material/icon';
import { AutenticacionService } from '../../services/autenticacion/autenticacion.service';
import { Usuario } from '../../services/Usuario';
import { Token } from '../../services/Token';

@Component({
  selector: 'app-inicio-sesion',
  standalone: true,
  imports: [ReactiveFormsModule, MatFormFieldModule, MatCardModule, MatInputModule, MatButtonModule, MatSnackBarModule, MatIconModule],
  templateUrl: './inicio-sesion.component.html',
  styleUrl: './inicio-sesion.component.css'
})

export class InicioSesionComponent implements OnInit {
  formularioInicioSesion!: FormGroup;
  errorInicioSesion: string = "";

  constructor(private readonly formBuilder: FormBuilder, private readonly snackBar: MatSnackBar, private readonly router: Router, private readonly autenticacionService: AutenticacionService) { }

  ngOnInit(): void {
    this.formularioInicioSesion = this.formBuilder.group({
      nombreUsuario: ['', Validators.required],
      contrasena: ['', Validators.required]
    });
  }

  iniciarSesion(formularioInicioSesion: FormGroup) {
    this.autenticacionService.inicioSesion(formularioInicioSesion.value as Usuario).subscribe({
      next: (datosToken: Token) => {
        this.autenticacionService.guardarTokenLocalStorage(datosToken.token);
        
        this.autenticacionService.usuarioEnSesion().subscribe({
          next: (usuario: Usuario) => {
            this.autenticacionService.guardarUsuarioLocalStorage(usuario);
            this.autenticacionService.sesionEstatusSubject.next(true);

            this.snackBar.open('Sesión iniciada con éxito, bienvenido ' + usuario.nombreUsuario + '.', 'Aceptar', {
              duration: 5000,
              verticalPosition: 'top',
              horizontalPosition: 'right',
              panelClass: ['success-snackbar']
            });
          },
          error: (datosError: any) => {
            this.snackBar.open('Error al obtener usuario en sesión.', 'Aceptar', {
              duration: 5000,
              verticalPosition: 'top',
              horizontalPosition: 'right'
            });
            this.errorInicioSesion = datosError.error.message;
          },
          complete: () => {
            
            // this.router.navigateByUrl('/home');
            this.router.navigate(['indice']);
          }
        } as any);
      },
      error: (datosError: any) => {
        if (datosError.status == 502) {
          this.snackBar.open('Usuario/Contraseña inválidos.', 'Aceptar', {
            duration: 5000,
            verticalPosition: 'top',
            horizontalPosition: 'right'
          });
        } else {
          this.snackBar.open('Error al obtener token de autenticación.', 'Aceptar', {
            duration: 5000,
            verticalPosition: 'top',
            horizontalPosition: 'right'
          });
        }
        this.errorInicioSesion = datosError.error.message;
      },
      complete: () => {
        
      }
    } as any);
  }

  get f(): { [key: string]: AbstractControl } {
    return this.formularioInicioSesion.controls;
  }
}
