package com.oghs.sgdsws.service;

import java.util.List;

import com.oghs.sgdsws.model.entity.Modulo;
import com.oghs.sgdsws.util.Paginado;

/**
 *
 * @author oghs
 */
public interface ModuloService {
    
    public List<Modulo> obtenerModulos();

    public Paginado<Modulo> obtenerModulosPaginado(int numeroPagina, int tamano);

    public void guardarModulo(Modulo modulo);

    public Modulo buscarModulo(Modulo modulo);

    public void eliminarModulo(Modulo modulo);
    
}
