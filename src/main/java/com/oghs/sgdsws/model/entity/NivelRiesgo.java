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
@Table(name = "NIVEL_RIESGO")
public class NivelRiesgo extends Auditable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_NIVEL_RIESGO")
    private Long idNivelRiesgo;

    @Column(name = "CODIGO")
    @NotEmpty
    private String codigo;

    @Column(name = "DESCRIPCION")
    @NotEmpty
    private String descripcion;

    @Column(name = "ESTATUS")
    @NotNull
    private Estatus estatus;

    @OneToMany(mappedBy = "nivelRiesgo")
    private Set<BitacoraProyecto> bitacoraProyecto;

    public Long getIdNivelRiesgo() {
        return idNivelRiesgo;
    }

    public void setIdNivelRiesgo(Long idNivelRiesgo) {
        this.idNivelRiesgo = idNivelRiesgo;
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
        return "NivelRiesgo [idNivelRiesgo=" + idNivelRiesgo + ", codigo=" + codigo + ", descripcion=" + descripcion
                + ", estatus=" + estatus + ", bitacoraProyecto=" + bitacoraProyecto + "]";
    }
    
}
