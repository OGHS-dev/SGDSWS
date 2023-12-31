package com.oghs.sgdsws.util;

import java.util.ArrayList;
import java.util.List;

public class Paginando {
    private static final int PAGINATION_STEP = 3;

    private boolean siguienteHabilitada;
    private boolean anteriorHabilitada;
    private int tamanoPagina;
    private int numeroPagina;

    private List<ElementoPagina> elementos = new ArrayList<>();

    public Paginando() {

    }

    public Paginando(boolean siguienteHabilitada, boolean anteriorHabilitada, int tamanoPagina, int numeroPagina, List<ElementoPagina> elementos) {
        this.siguienteHabilitada = siguienteHabilitada;
        this.anteriorHabilitada = anteriorHabilitada;
        this.tamanoPagina = tamanoPagina;
        this.numeroPagina = numeroPagina;
        this.elementos = elementos;
    }

    public void agregarElementosPagina(int desde, int hasta, int numeroPagina) {
        for (int i = desde; i < hasta; i++) {
            elementos.add(new ElementoPagina(TipoElementoPagina.PAGINA, i, numeroPagina != i));
        }
    }

    public void ultima(int tamanoPagina) {
        elementos.add(new ElementoPagina(TipoElementoPagina.PUNTOS, 0, false));
        elementos.add(new ElementoPagina(TipoElementoPagina.PAGINA, tamanoPagina, true));
    }

    public void primera(int numeroPagina) {
        elementos.add(new ElementoPagina(TipoElementoPagina.PAGINA, 1, numeroPagina != 1));
        elementos.add(new ElementoPagina(TipoElementoPagina.PUNTOS, 0, false));
    }

    public static Paginando of(int totalPaginas, int numeroPagina, int tamanoPagina) {
        Paginando paginando = new Paginando();
        paginando.setTamanoPagina(tamanoPagina);
        paginando.setSiguienteHabilitada(numeroPagina != totalPaginas);
        paginando.setAnteriorHabilitada(numeroPagina != 1);
        paginando.setNumeroPagina(numeroPagina);

        if (totalPaginas < PAGINATION_STEP * 2 + 6) {
            paginando.agregarElementosPagina(1, totalPaginas + 1, numeroPagina);

        } else if (numeroPagina < PAGINATION_STEP * 2 + 1) {
            paginando.agregarElementosPagina(1, PAGINATION_STEP * 2 + 4, numeroPagina);
            paginando.ultima(totalPaginas);

        } else if (numeroPagina > totalPaginas - PAGINATION_STEP * 2) {
            paginando.primera(numeroPagina);
            paginando.agregarElementosPagina(totalPaginas - PAGINATION_STEP * 2 - 2, totalPaginas + 1, numeroPagina);

        } else {
            paginando.primera(numeroPagina);
            paginando.agregarElementosPagina(numeroPagina - PAGINATION_STEP, numeroPagina + PAGINATION_STEP + 1, numeroPagina);
            paginando.ultima(totalPaginas);
        }

        return paginando;
    }

    public boolean isSiguienteHabilitada() {
        return siguienteHabilitada;
    }

    public void setSiguienteHabilitada(boolean siguienteHabilitada) {
        this.siguienteHabilitada = siguienteHabilitada;
    }

    public boolean isAnteriorHabilitada() {
        return anteriorHabilitada;
    }

    public void setAnteriorHabilitada(boolean anteriorHabilitada) {
        this.anteriorHabilitada = anteriorHabilitada;
    }

    public int getTamanoPagina() {
        return tamanoPagina;
    }

    public void setTamanoPagina(int tamanoPagina) {
        this.tamanoPagina = tamanoPagina;
    }

    public int getNumeroPagina() {
        return numeroPagina;
    }

    public void setNumeroPagina(int numeroPagina) {
        this.numeroPagina = numeroPagina;
    }

    public List<ElementoPagina> getElementos() {
        return elementos;
    }

    public void setElementos(List<ElementoPagina> elementos) {
        this.elementos = elementos;
    }

}
