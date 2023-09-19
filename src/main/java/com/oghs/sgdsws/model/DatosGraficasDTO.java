package com.oghs.sgdsws.model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author oghs
 */
public class DatosGraficasDTO implements Serializable {
    List<Object> listaModulos;

    List<Object> listaHallazgos;

    List<Object> listaIncidentes;

    List<Object> listaCategorias;
    
    List<Object> listaPrioridades;

    List<Object> listaImpactos;

    List<Object> listaNivelesRiesgo;

    List<Object> listaEstadosBitacoraProyecto;

    public List<Object> getListaModulos() {
        return listaModulos;
    }

    public void setListaModulos(List<Object> listaModulos) {
        this.listaModulos = listaModulos;
    }

    public List<Object> getListaHallazgos() {
        return listaHallazgos;
    }

    public void setListaHallazgos(List<Object> listaHallazgos) {
        this.listaHallazgos = listaHallazgos;
    }

    public List<Object> getListaIncidentes() {
        return listaIncidentes;
    }

    public void setListaIncidentes(List<Object> listaIncidentes) {
        this.listaIncidentes = listaIncidentes;
    }

    public List<Object> getListaCategorias() {
        return listaCategorias;
    }

    public void setListaCategorias(List<Object> listaCategorias) {
        this.listaCategorias = listaCategorias;
    }

    public List<Object> getListaPrioridades() {
        return listaPrioridades;
    }

    public void setListaPrioridades(List<Object> listaPrioridades) {
        this.listaPrioridades = listaPrioridades;
    }

    public List<Object> getListaImpactos() {
        return listaImpactos;
    }

    public void setListaImpactos(List<Object> listaImpactos) {
        this.listaImpactos = listaImpactos;
    }

    public List<Object> getListaNivelesRiesgo() {
        return listaNivelesRiesgo;
    }

    public void setListaNivelesRiesgo(List<Object> listaNivelesRiesgo) {
        this.listaNivelesRiesgo = listaNivelesRiesgo;
    }

    public List<Object> getListaEstadosBitacoraProyecto() {
        return listaEstadosBitacoraProyecto;
    }

    public void setListaEstadosBitacoraProyecto(List<Object> listaEstadosBitacoraProyecto) {
        this.listaEstadosBitacoraProyecto = listaEstadosBitacoraProyecto;
    }
    
}
