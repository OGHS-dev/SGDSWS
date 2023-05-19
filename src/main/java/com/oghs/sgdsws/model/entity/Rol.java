package com.oghs.sgdsws.model.entity;

import java.io.Serializable;
import java.util.Set;

import com.oghs.sgdsws.model.Estatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 *
 * @author oghs
 */
@Entity
@Table(name = "ROL")
public class Rol extends Auditable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ROL")
    private Long idRol;

    @Column(name = "CODIGO")
    @NotEmpty
    private String codigo;

    @Column(name = "DESCRIPCION")
    @NotEmpty
    private String descripcion;

    @Column(name = "ESTATUS")
    @NotNull
    private Estatus estatus;

    @OneToMany(mappedBy = "rol")
    private Set<Usuario> usuario;

    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Estatus getEstatus() {
        return estatus;
    }

    public void setEstatus(Estatus estatus) {
        this.estatus = estatus;
    }

    public Set<Usuario> getUsuario() {
        return usuario;
    }

    public void setUsuario(Set<Usuario> usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "Rol [idRol=" + idRol + ", codigo=" + codigo + ", descripcion=" + descripcion + ", estatus=" + estatus
                + ", usuario=" + usuario + "]";
    }

}
