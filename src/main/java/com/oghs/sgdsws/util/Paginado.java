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
        int result = 1;
        result = prime * result + ((page == null) ? 0 : page.hashCode());
        result = prime * result + ((paginando == null) ? 0 : paginando.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Paginado other = (Paginado) obj;
        if (page == null) {
            if (other.page != null)
                return false;
        } else if (!page.equals(other.page))
            return false;
        if (paginando == null) {
            if (other.paginando != null)
                return false;
        } else if (!paginando.equals(other.paginando))
            return false;
        return true;
    }
    
}
