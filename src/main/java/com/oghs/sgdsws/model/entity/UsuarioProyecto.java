package com.oghs.sgdsws.model.entity;

import java.io.Serializable;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

/**
 *
 * @author oghs
 */
@Entity
@Table(name = "USUARIO_PROYECTO")
public class UsuarioProyecto implements Serializable {
    @EmbeddedId
    private UsuarioProyectoId usuarioProyectoId;

    @ManyToOne
    @MapsId("ID_USUARIO")
    @JoinColumn(name = "ID_USUARIO")
    private Usuario usuario;
    @ManyToOne
    @MapsId("ID_PROYECTO")
    @JoinColumn(name = "ID_PROYECTO")
    private Proyecto proyecto;

    public UsuarioProyecto() {
    }
    
    public UsuarioProyecto(UsuarioProyectoId usuarioProyectoId, Usuario usuario, Proyecto proyecto) {
        this.usuarioProyectoId = usuarioProyectoId;
        this.usuario = usuario;
        this.proyecto = proyecto;
    }
    
    public UsuarioProyectoId getUsuarioProyectoId() {
        return usuarioProyectoId;
    }
    public void setUsuarioProyectoId(UsuarioProyectoId usuarioProyectoId) {
        this.usuarioProyectoId = usuarioProyectoId;
    }
    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public Proyecto getProyecto() {
        return proyecto;
    }
    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

}
