package com.oghs.sgdsws.model;

/**
 *
 * @author oghs
 */
public enum Estatus {
    INACTIVO("Inactivo", 0), 
    ACTIVO("Activo", 1);

    private final String etiqueta;
    private final int valor;

    Estatus(String etiqueta, int valor) {
        this.etiqueta = etiqueta;
        this.valor = valor;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public int getValor() {
        return valor;
    }

}
