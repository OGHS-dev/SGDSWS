import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatTableModule } from '@angular/material/table';
import { RouterLink } from '@angular/router';
import { Proyecto } from '../../services/Proyecto';
import { ProyectoService } from '../../services/proyecto/proyecto.service';

@Component({
  selector: 'app-proyecto',
  standalone: true,
  imports: [CommonModule, MatTableModule, MatProgressSpinnerModule, RouterLink],
  templateUrl: './proyecto.component.html',
  styleUrl: './proyecto.component.css'
})
export class ProyectoComponent implements OnInit {
  proyectos: Proyecto[] = [];
  cargando = true;
  error = '';

  constructor(private readonly proyectoService: ProyectoService) {}

  ngOnInit(): void {
    this.proyectoService.listarProyectos().subscribe({
      next: proyectos => {
        this.proyectos = proyectos;
        this.cargando = false;
      },
      error: () => {
        this.error = 'No fue posible cargar los proyectos.';
        this.cargando = false;
      }
    });
  }
}
