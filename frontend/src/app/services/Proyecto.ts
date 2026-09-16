export interface Proyecto {
  idProyecto?: number;
  nombre: string;
  descripcion: string;
  idEstadoProyecto: number;
  estadoProyecto?: string;
  responsable: string;
  fechaInicio: string;
  fechaFin: string;
  idsUsuarios: number[];
}
