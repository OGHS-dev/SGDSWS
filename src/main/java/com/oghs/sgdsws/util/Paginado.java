package com.oghs.sgdsws.util;

import org.springframework.data.domain.Page;

public class Paginado<T> {

    private Page<T> page;

    private Paginando paginando;

    public Paginado() {
        
    }

    public Paginado(Page<T> page, Paginando paginando) {
        this.page = page;
        this.paginando = paginando;
    }

    public Page<T> getPage() {
        return page;
    }

    public void setPage(Page<T> page) {
        this.page = page;
    }

    public Paginando getPaginando() {
        return paginando;
    }

    public void setPaginando(Paginando paginando) {
        this.paginando = paginando;
    }

    @Override
    public String toString() {
        return "Paginado [page=" + page + ", paginando=" + paginando + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int resultado = 1;
        resultado = prime * resultado + ((page == null) ? 0 : page.hashCode());
        resultado = prime * resultado + ((paginando == null) ? 0 : paginando.hashCode());
        return resultado;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null)
            return false;
        if (getClass() != object.getClass())
            return false;
        Paginado otra = (Paginado) object;
        if (page == null) {
            if (otra.page != null)
                return false;
        } else if (!page.equals(otra.page))
            return false;
        if (paginando == null) {
            if (otra.paginando != null)
                return false;
        } else if (!paginando.equals(otra.paginando))
            return false;
        return true;
    }
    
}
