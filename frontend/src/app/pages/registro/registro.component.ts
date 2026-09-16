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

@Component({
  selector: 'app-registro',
  standalone: true,
  imports: [ReactiveFormsModule, MatFormFieldModule, MatCardModule, MatInputModule, MatButtonModule, MatSnackBarModule, MatIconModule],
  templateUrl: './registro.component.html',
  styleUrl: './registro.component.css'
})

export class RegistroComponent implements OnInit {
  formularioRegistro!: FormGroup;
  errorRegistro: string = "";

  constructor(private readonly formBuilder: FormBuilder, private readonly snackBar: MatSnackBar, private readonly router: Router, private readonly autenticacionService: AutenticacionService) { }

  ngOnInit(): void {
    this.formularioRegistro = this.formBuilder.group({
      nombreUsuario: ['', Validators.required],
      contrasena: ['', Validators.required],
      correo: ['', [Validators.required, Validators.email]]
    });
  }

  registrarUsuario(formularioRegistro: FormGroup) {
    this.autenticacionService.registro(formularioRegistro.value as Usuario).subscribe({
      next: (datosUsuario) => {
        console.log('NEXT -> ', datosUsuario);
        if (datosUsuario != null) {
          this.snackBar.open('Usuario registrado con éxito.', 'Aceptar', {
            duration: 5000,
            verticalPosition: 'top',
            horizontalPosition: 'right'
          });
        } else {
          this.snackBar.open('El nombre de usuario ya existe.', 'Aceptar', {
            duration: 5000,
            verticalPosition: 'top',
            horizontalPosition: 'right'
          });
        }
      },
      error: (datosError) => {
        console.error('ERROR -> ', datosError.error.message);
        this.snackBar.open('Error al registrar usuario.', 'Aceptar', {
          duration: 5000,
          verticalPosition: 'top',
          horizontalPosition: 'right'
        });
        // this.errorInicioSesion = datosError;
      },
      complete: () => {
        console.info('Registro completo -> redireccionando a home');
        // this.router.navigateByUrl('/home');
        this.router.navigate(['indice']);
      }
    })
  }

  get f(): { [key: string]: AbstractControl } {
    return this.formularioRegistro.controls;
  }
}
