import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { BitacoraProyecto } from '../../services/BitacoraProyecto';
import { BitacoraService } from '../../services/bitacora/bitacora.service';
import { Proyecto } from '../../services/Proyecto';
import { ProyectoService } from '../../services/proyecto/proyecto.service';

@Component({
  selector: 'app-proyecto-detalle',
  standalone: true,
  imports: [CommonModule, RouterLink, MatProgressSpinnerModule],
  templateUrl: './proyecto-detalle.component.html',
  styleUrl: './proyecto-detalle.component.css'
})
export class ProyectoDetalleComponent implements OnInit {
  proyecto: Proyecto | null = null;
  bitacoras: BitacoraProyecto[] = [];
  cargando = true;
  error = '';

  constructor(
    private readonly route: ActivatedRoute,
    private readonly proyectoService: ProyectoService,
    private readonly bitacoraService: BitacoraService
  ) {}

  ngOnInit(): void {
    const idProyecto = Number(this.route.snapshot.paramMap.get('idProyecto'));
    this.proyectoService.buscarProyecto(idProyecto).subscribe({
      next: proyecto => {
        this.proyecto = proyecto;
        this.bitacoraService.listar(idProyecto).subscribe({
          next: bitacoras => {
            this.bitacoras = bitacoras;
            this.cargando = false;
          },
          error: () => {
            this.error = 'No fue posible cargar la bitácora del proyecto.';
            this.cargando = false;
          }
        });
      },
      error: () => {
        this.error = 'No fue posible cargar el proyecto.';
        this.cargando = false;
      }
    });
  }
}
