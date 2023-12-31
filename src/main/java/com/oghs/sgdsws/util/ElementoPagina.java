package com.oghs.sgdsws.util;

public class ElementoPagina {
    
    private TipoElementoPagina tipoElementoPagina;

    private int indice;

    private boolean activa;

    public ElementoPagina() {
        
    }

    public ElementoPagina(TipoElementoPagina tipoElementoPagina, int indice, boolean activa) {
        this.tipoElementoPagina = tipoElementoPagina;
        this.indice = indice;
        this.activa = activa;
    }

    public TipoElementoPagina getTipoElementoPagina() {
        return tipoElementoPagina;
    }

    public void setTipoElementoPagina(TipoElementoPagina tipoElementoPagina) {
        this.tipoElementoPagina = tipoElementoPagina;
    }

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }
    
}
