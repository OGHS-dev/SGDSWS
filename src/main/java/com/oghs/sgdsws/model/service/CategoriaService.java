package com.oghs.sgdsws.model.service;

import java.util.List;

import com.oghs.sgdsws.model.entity.Categoria;
import com.oghs.sgdsws.util.Paginado;

/**
 *
 * @author oghs
 */
public interface CategoriaService {
    
    public List<Categoria> obtenerCategorias();

    public Paginado<Categoria> obtenerCategoriasPaginado(int numeroPagina, int tamano);

    public void guardarCategoria(Categoria categoria);

    public Categoria buscarCategoria(Categoria categoria);

    public void eliminarCategoria(Categoria categoria);
    
}
