package com.oghs.sgdsws.service;

import java.util.List;

import com.oghs.sgdsws.model.entity.Hallazgo;
import com.oghs.sgdsws.util.Paginado;

/**
 *
 * @author oghs
 */
public interface HallazgoService {

    public List<Hallazgo> obtenerHallazgos();

    public Paginado<Hallazgo> obtenerHallazgosPaginado(int numeroPagina, int tamano);

    public void guardarHallazgo(Hallazgo hallazgo);

    public Hallazgo buscarHallazgo(Hallazgo hallazgo);

    public void eliminarHallazgo(Hallazgo hallazgo);
    
}
