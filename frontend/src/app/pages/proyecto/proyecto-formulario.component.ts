import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { forkJoin, of } from 'rxjs';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { Catalogo } from '../../services/Catalogo';
import { CatalogoService } from '../../services/catalogo/catalogo.service';
import { Proyecto } from '../../services/Proyecto';
import { ProyectoService } from '../../services/proyecto/proyecto.service';
import { Usuario } from '../../services/Usuario';
import { UsuarioService } from '../../services/usuario/usuario.service';

@Component({
  selector: 'app-proyecto-formulario',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink, MatProgressSpinnerModule],
  templateUrl: './proyecto-formulario.component.html',
  styleUrl: './proyecto-formulario.component.css'
})
export class ProyectoFormularioComponent implements OnInit {
  private readonly formBuilder = inject(FormBuilder);
  readonly form = this.formBuilder.group({
    nombre: ['', [Validators.required, Validators.maxLength(100)]],
    descripcion: ['', [Validators.required, Validators.maxLength(200)]],
    idEstadoProyecto: [null as number | null, Validators.required],
    responsable: ['', Validators.required],
    fechaInicio: ['', Validators.required],
    fechaFin: ['', Validators.required],
    idsUsuarios: [[] as number[]]
  });
  estados: Catalogo[] = [];
  usuarios: Usuario[] = [];
  idProyecto: number | null = null;
  cargando = true;
  guardando = false;
  error = '';

  constructor(
    private readonly route: ActivatedRoute,
    private readonly router: Router,
    private readonly proyectoService: ProyectoService,
    private readonly catalogoService: CatalogoService,
    private readonly usuarioService: UsuarioService
  ) {}

  ngOnInit(): void {
    this.idProyecto = this.route.snapshot.paramMap.get('idProyecto')
      ? Number(this.route.snapshot.paramMap.get('idProyecto')) : null;
    const proyecto$ = this.idProyecto
      ? this.proyectoService.buscarProyecto(this.idProyecto)
      : of(null);
    forkJoin({
      estados: this.catalogoService.listarEstadosProyecto(),
      usuarios: this.usuarioService.listarUsuarios(),
      proyecto: proyecto$
    }).subscribe({
      next: data => {
        this.estados = data.estados;
        this.usuarios = data.usuarios;
        if (data.proyecto) {
          const proyecto = data.proyecto;
          this.form.patchValue({
            nombre: proyecto.nombre,
            descripcion: proyecto.descripcion,
            idEstadoProyecto: proyecto.idEstadoProyecto,
            responsable: proyecto.responsable,
            fechaInicio: proyecto.fechaInicio,
            fechaFin: proyecto.fechaFin,
            idsUsuarios: proyecto.idsUsuarios
          });
        }
        this.cargando = false;
      },
      error: () => {
        this.error = 'No fue posible cargar los datos del formulario.';
        this.cargando = false;
      }
    });
  }

  guardar(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
    this.guardando = true;
    const proyecto = this.form.getRawValue() as Proyecto;
    const request$ = this.idProyecto
      ? this.proyectoService.actualizarProyecto(this.idProyecto, proyecto)
      : this.proyectoService.crearProyecto(proyecto);
    request$.subscribe({
      next: result => this.router.navigate(['/proyecto', result.idProyecto]),
      error: () => {
        this.error = 'No fue posible guardar el proyecto.';
        this.guardando = false;
      }
    });
  }
}
