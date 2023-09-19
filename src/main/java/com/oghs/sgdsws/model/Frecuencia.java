package com.oghs.sgdsws.model;

/**
 *
 * @author oghs
 */
public enum Frecuencia {
    nunca("Nunca"), 
    ocasionalmente("Ocasionalmente"), 
    frecuentemente("Frecuentemente"), 
    siempre("Siempre");

    public final String etiqueta;

    private Frecuencia(String etiqueta) {
        this.etiqueta = etiqueta;
    }
}

