package com.oghs.sgdsws.model;

/**
 *
 * @author oghs
 */
public enum Frecuencia {
    NUNCA("Nunca"), 
    OCASIONALMENTE("Ocasionalmente"), 
    FRECUENTEMENTE("Frecuentemente"), 
    SIEMPRE("Siempre"), 
    NO_APLICA("No Aplica");

    private final String etiqueta;

    Frecuencia(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getEtiqueta() {
        return etiqueta;
    }
    
}

