package com.oghs.sgdsws.model.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 *
 * @author oghs
 */
@Embeddable
public class UsuarioProyectoId implements Serializable {
    @Column(name = "ID_USUARIO")
    private Long idUsuario;
    @Column(name = "ID_PROYECTO")
    private Long idProyecto;

    public UsuarioProyectoId() {
    }

    public UsuarioProyectoId(Long idUsuario, Long idProyecto) {
        this.idUsuario = idUsuario;
        this.idProyecto = idProyecto;
    }
    
    public Long getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
    public Long getIdProyecto() {
        return idProyecto;
    }
    public void setIdProyecto(Long idProyecto) {
        this.idProyecto = idProyecto;
    }

    @Override
    public String toString() {
        return "UsuarioProyectoId [idUsuario=" + idUsuario + ", idProyecto=" + idProyecto + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idUsuario == null) ? 0 : idUsuario.hashCode());
        result = prime * result + ((idProyecto == null) ? 0 : idProyecto.hashCode());
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
        UsuarioProyectoId other = (UsuarioProyectoId) obj;
        if (idUsuario == null) {
            if (other.idUsuario != null)
                return false;
        } else if (!idUsuario.equals(other.idUsuario))
            return false;
        if (idProyecto == null) {
            if (other.idProyecto != null)
                return false;
        } else if (!idProyecto.equals(other.idProyecto))
            return false;
        return true;
    }
    
}
