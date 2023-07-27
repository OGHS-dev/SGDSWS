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
import jakarta.validation.constraints.Size;

/**
 *
 * @author oghs
 */
@Entity
@Table(name = "ESTADO_PROYECTO")
public class EstadoProyecto extends Auditable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ESTADO_PROYECTO")
    private Long idEstadoProyecto;

    @Column(name = "CODIGO")
    @NotEmpty(message = "{NotEmpty.EstadoProyecto.codigo}")
    @Size(min = 1, max = 4, message = "{Size.EstadoProyecto.codigo}")
    private String codigo;

    @Column(name = "DESCRIPCION")
    @NotEmpty(message = "{NotEmpty.EstadoProyecto.descripcion}")
    @Size(min = 1, max = 20, message = "{Size.EstadoProyecto.descripcion}")
    private String descripcion;

    @Column(name = "ESTATUS")
    @NotNull(message = "{NotNull.EstadoProyecto.estatus}")
    private Estatus estatus;

    @OneToMany(mappedBy = "estadoProyecto")
    private Set<Proyecto> proyecto;

    public Long getIdEstadoProyecto() {
        return idEstadoProyecto;
    }

    public void setIdEstadoProyecto(Long idEstadoProyecto) {
        this.idEstadoProyecto = idEstadoProyecto;
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

    public Set<Proyecto> getProyecto() {
        return proyecto;
    }

    public void setProyecto(Set<Proyecto> proyecto) {
        this.proyecto = proyecto;
    }

    @Override
    public String toString() {
        return "EstadoProyecto [idEstadoProyecto=" + idEstadoProyecto + ", codigo=" + codigo + ", descripcion="
                + descripcion + ", estatus=" + estatus + "]";
    }
    
}
