package com.oghs.sgdsws.model.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import com.oghs.sgdsws.model.EstatusProyecto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

/**
 *
 * @author oghs
 */
@Entity
@Table(name = "PROYECTO")
public class Proyecto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PROYECTO")
    private Long idProyecto;

    @Column(name = "NOMBRE")
    @NotEmpty
    private String nombre;

    @Column(name = "DESCRIPCION")
    @NotEmpty
    private String descripcion;

    @Column(name = "ESTATUS")
    @NotNull
    private EstatusProyecto estatus;

    @Column(name = "FECHA_CREACION", nullable = false, updatable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @CreationTimestamp
    private Date fechaCreacion;

    @Column(name = "FECHA_INICIO")
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaInicio;
    
    @Column(name = "FECHA_FIN")
    @NotNull
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

    public EstatusProyecto getEstatus() {
        return estatus;
    }

    public void setEstatus(EstatusProyecto estatus) {
        this.estatus = estatus;
    }

    public Set<UsuarioProyecto> getUsuarioProyecto() {
        return usuarioProyecto;
    }

    public void setUsuarioProyecto(Set<UsuarioProyecto> usuarioProyecto) {
        this.usuarioProyecto = usuarioProyecto;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
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

    public Set<BitacoraProyecto> getBitacoraProyecto() {
        return bitacoraProyecto;
    }

    public void setBitacoraProyecto(Set<BitacoraProyecto> bitacoraProyecto) {
        this.bitacoraProyecto = bitacoraProyecto;
    }

    @Override
    public String toString() {
        return "Proyecto [idProyecto=" + idProyecto + ", nombre=" + nombre + ", descripcion=" + descripcion
                + ", estatus=" + estatus + ", fechaCreacion=" + fechaCreacion + ", fechaInicio=" + fechaInicio
                + ", fechaFin=" + fechaFin + ", usuarioProyecto=" + usuarioProyecto + ", bitacoraProyecto="
                + bitacoraProyecto + "]";
    }
    
}
