package com.oghs.sgdsws.service;

import java.util.List;

import com.oghs.sgdsws.model.entity.Impacto;
import com.oghs.sgdsws.util.Paginado;

/**
 *
 * @author oghs
 */
public interface ImpactoService {
    
    public List<Impacto> obtenerImpactos();

    public Paginado<Impacto> obtenerImpactosPaginado(int numeroPagina, int tamano);

    public void guardarImpacto(Impacto impacto);

    public Impacto buscarImpacto(Impacto impacto);

    public void eliminarImpacto(Impacto impacto);
    
}
