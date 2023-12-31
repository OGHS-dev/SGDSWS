package com.oghs.sgdsws.model.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "TBL_PROYECTO")
public class Proyecto extends Auditable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PROYECTO")
    private Long idProyecto;

    @Column(name = "NOMBRE")
    @NotEmpty(message = "{NotEmpty.Proyecto.nombre}")
    @Size(min = 1, max = 100, message = "{Size.Proyecto.nombre}")
    private String nombre;

    @Column(name = "DESCRIPCION")
    @NotEmpty(message = "{NotEmpty.Proyecto.descripcion}")
    @Size(min = 1, max = 200, message = "{Size.Proyecto.descripcion}")
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "ID_ESTADO_PROYECTO")
    private EstadoProyecto estadoProyecto;

    @Column(name = "USUARIO_ASIGNADO")
    @NotEmpty(message = "{NotEmpty.Proyecto.usuarioAsignado}")
    private String usuarioAsignado;

    @Column(name = "FECHA_INICIO")
    @NotNull(message = "{NotNull.Proyecto.fechaInicio}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaInicio;
    
    @Column(name = "FECHA_FIN")
    @NotNull(message = "{NotNull.Proyecto.fechaFin}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaFin;

    @OneToMany(mappedBy = "proyecto")
    private Set<UsuarioProyecto> usuarioProyecto;

    @OneToMany(mappedBy = "proyecto")
    private Set<BitacoraProyecto> bitacoraProyecto;

    public Long getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(Long idProyecto) {
        this.idProyecto = idProyecto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public EstadoProyecto getEstadoProyecto() {
        return estadoProyecto;
    }

    public void setEstadoProyecto(EstadoProyecto estadoProyecto) {
        this.estadoProyecto = estadoProyecto;
    }

    public String getUsuarioAsignado() {
        return usuarioAsignado;
    }

    public void setUsuarioAsignado(String usuarioAsignado) {
        this.usuarioAsignado = usuarioAsignado;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Set<UsuarioProyecto> getUsuarioProyecto() {
        return usuarioProyecto;
    }

    public void setUsuarioProyecto(Set<UsuarioProyecto> usuarioProyecto) {
        this.usuarioProyecto = usuarioProyecto;
    }

    public Set<BitacoraProyecto> getBitacoraProyecto() {
        return bitacoraProyecto;
    }

    public void setBitacoraProyecto(Set<BitacoraProyecto> bitacoraProyecto) {
        this.bitacoraProyecto = bitacoraProyecto;
    }

    @Override
    public String toString() {
        return "Proyecto [idProyecto=" + idProyecto + ", nombre=" + nombre + ", descripcion=" + descripcion
                + ", usuarioAsignado=" + usuarioAsignado + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin
                + ", usuarioProyecto=" + usuarioProyecto + "]";
    }

}
