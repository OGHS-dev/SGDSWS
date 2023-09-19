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
@Table(name = "TBL_CATEGORIA")
public class Categoria extends Auditable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CATEGORIA")
    private Long idCategoria;

    @Column(name = "CODIGO")
    @NotEmpty(message = "{NotEmpty.Categoria.codigo}")
    @Size(min = 1, max = 4, message = "{Size.Categoria.codigo}")
    private String codigo;

    @Column(name = "DESCRIPCION")
    @NotEmpty(message = "{NotEmpty.Categoria.descripcion}")
    @Size(min = 1, max = 20, message = "{Size.Categoria.descripcion}")
    private String descripcion;

    @Column(name = "ESTATUS")
    @NotNull(message = "{NotNull.Categoria.estatus}")
    private Estatus estatus;

    @OneToMany(mappedBy = "categoria")
    private Set<BitacoraProyecto> bitacoraProyecto;

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
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

    public Set<BitacoraProyecto> getBitacoraProyecto() {
        return bitacoraProyecto;
    }

    public void setBitacoraProyecto(Set<BitacoraProyecto> bitacoraProyecto) {
        this.bitacoraProyecto = bitacoraProyecto;
    }

    @Override
    public String toString() {
        return "Categoria [idCategoria=" + idCategoria + ", codigo=" + codigo + ", descripcion=" + descripcion + ", estatus="
                + estatus + "]";
    }

}
