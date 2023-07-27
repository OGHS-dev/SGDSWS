package com.oghs.sgdsws.model.service;

import java.util.List;

import com.oghs.sgdsws.model.ProyectoDTO;
import com.oghs.sgdsws.model.entity.Archivo;
import com.oghs.sgdsws.model.entity.BitacoraProyecto;
import com.oghs.sgdsws.model.entity.Proyecto;
import com.oghs.sgdsws.util.Paginado;

/**
 *
 * @author oghs
 */
public interface BitacoraProyectoService {
    
    public List<BitacoraProyecto> obtenerBitacoraProyectoPorProyecto(Proyecto proyecto);

    public Paginado<BitacoraProyecto> obtenerBitacoraProyectoPorProyectoPaginado(Proyecto proyecto, int numeroPagina, int tamano);

    public void guardarBitacoraProyecto(ProyectoDTO proyectoDTO, List<Archivo> archivos);

    public BitacoraProyecto buscarBitacoraProyecto(BitacoraProyecto bitacoraProyecto);
    
}
