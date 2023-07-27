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
@Table(name = "IMPACTO")
public class Impacto extends Auditable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_IMPACTO")
    private Long idImpacto;

    @Column(name = "CODIGO")
    @NotEmpty(message = "{NotEmpty.Impacto.codigo}")
    @Size(min = 1, max = 4, message = "{Size.Impacto.codigo}")
    private String codigo;

    @Column(name = "DESCRIPCION")
    @NotEmpty(message = "{NotEmpty.Impacto.descripcion}")
    @Size(min = 1, max = 20, message = "{Size.Impacto.descripcion}")
    private String descripcion;

    @Column(name = "ESTATUS")
    @NotNull(message = "{NotNull.Impacto.estatus}")
    private Estatus estatus;

    @OneToMany(mappedBy = "impacto")
    private Set<BitacoraProyecto> bitacoraProyecto;

    public Long getIdImpacto() {
        return idImpacto;
    }

    public void setIdImpacto(Long idImpacto) {
        this.idImpacto = idImpacto;
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
        return "Impacto [idImpacto=" + idImpacto + ", codigo=" + codigo + ", descripcion=" + descripcion + ", estatus="
                + estatus + "]";
    }
    
}
