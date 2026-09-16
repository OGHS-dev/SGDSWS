import { Catalogo } from './Catalogo';

export interface BitacoraProyecto {
  idBitacoraProyecto?: number;
  idProyecto: number;
  fechaBitacora?: string;
  usuarioReporte?: string;
  revision?: number;
  descripcion: string;
  componente?: string;
  version?: string;
  frecuencia?: string;
  modulo?: Catalogo;
  hallazgo?: Catalogo;
  incidente?: Catalogo;
  categoria?: Catalogo;
  prioridad?: Catalogo;
  impacto?: Catalogo;
  nivelRiesgo?: Catalogo;
  estadoBitacoraProyecto?: Catalogo;
  acciones?: string;
  usuarioAsignado?: string;
}
