package com.oghs.sgdsws.service;

import java.util.List;

import com.oghs.sgdsws.model.entity.NivelRiesgo;
import com.oghs.sgdsws.util.Paginado;

/**
 *
 * @author oghs
 */
public interface NivelRiesgoService {

    public List<NivelRiesgo> obtenerNivelesRiesgo();

    public Paginado<NivelRiesgo> obtenerNivelesRiesgoPaginado(int numeroPagina, int tamano);

    public void guardarNivelRiesgo(NivelRiesgo nivelRiesgo);

    public NivelRiesgo buscarNivelRiesgo(NivelRiesgo nivelRiesgo);

    public void eliminarNivelRiesgo(NivelRiesgo nivelRiesgo);
    
}
