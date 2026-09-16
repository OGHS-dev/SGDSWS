import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatTableModule } from '@angular/material/table';
import { BitacoraProyecto } from '../../../services/BitacoraProyecto';
import { BitacoraService } from '../../../services/bitacora/bitacora.service';
import { Archivo } from '../../../services/Archivo';
import { Comentario } from '../../../services/Comentario';
import { RecursoProyectoService } from '../../../services/recurso-proyecto/recurso-proyecto.service';

@Component({
  selector: 'app-bitacora',
  standalone: true,
  imports: [CommonModule, MatProgressSpinnerModule, MatTableModule],
  templateUrl: './bitacora.component.html',
  styleUrl: './bitacora.component.css'
})
export class BitacoraComponent implements OnInit {
  idProyecto = 0;
  bitacoras: BitacoraProyecto[] = [];
  cargando = true;
  error = '';
  archivos: Record<number, Archivo[]> = {};
  comentarios: Record<number, Comentario[]> = {};
  readonly columnas = ['revision', 'fecha', 'descripcion', 'estado', 'usuario'];

  constructor(
    private readonly route: ActivatedRoute,
    private readonly bitacoraService: BitacoraService,
    private readonly recursoService: RecursoProyectoService
  ) {}

  ngOnInit(): void {
    this.idProyecto = Number(this.route.snapshot.paramMap.get('idProyecto'));
    this.bitacoraService.listar(this.idProyecto).subscribe({
      next: bitacoras => {
        this.bitacoras = bitacoras;
        this.cargando = false;
        bitacoras.forEach(bitacora => {
          if (bitacora.idBitacoraProyecto) {
            this.cargarRecursos(bitacora.idBitacoraProyecto);
          }
        });
      },
      error: () => {
        this.error = 'No fue posible cargar la bitácora del proyecto.';
        this.cargando = false;
      }
    });
  }

  cargarRecursos(idBitacoraProyecto: number): void {
    this.recursoService.listarArchivos(idBitacoraProyecto).subscribe(archivos => {
      this.archivos[idBitacoraProyecto] = archivos;
    });
    this.recursoService.listarComentarios(idBitacoraProyecto).subscribe(comentarios => {
      this.comentarios[idBitacoraProyecto] = comentarios;
    });
  }

  descargarReporte(): void {
    this.recursoService.descargarReporte(this.idProyecto).subscribe(blob => {
      const url = URL.createObjectURL(blob);
      const anchor = document.createElement('a');
      anchor.href = url;
      anchor.download = `reporte-proyecto-${this.idProyecto}.xlsx`;
      anchor.click();
      URL.revokeObjectURL(url);
    });
  }
}
