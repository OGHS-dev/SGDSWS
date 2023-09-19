package com.oghs.sgdsws.model.entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 *
 * @author oghs
 */
@Entity
@Table(name = "TBL_ARCHIVO")
public class Archivo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ARCHIVO")
    private Long idArchivo;
    
    @ManyToOne
    @JoinColumn(name = "ID_BITACORA_PROYECTO")
    @JsonBackReference
    private BitacoraProyecto bitacoraProyecto;

    @Column(name = "NOMBRE_ARCHIVO")
    private String nombreArchivo;

    @Column(name = "TAMANO_ARCHIVO")
    private Long tamanoArchivo;

    @Lob
    @Column(name = "ARCHIVO")
    private byte[] archivo;

    @Column(name = "FECHA_CREACION", nullable = false, updatable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @CreationTimestamp
    private Date fechaCreacion;

    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;

    public Long getIdArchivo() {
        return idArchivo;
    }

    public void setIdArchivo(Long idArchivo) {
        this.idArchivo = idArchivo;
    }

    public BitacoraProyecto getBitacoraProyecto() {
        return bitacoraProyecto;
    }

    public void setBitacoraProyecto(BitacoraProyecto bitacoraProyecto) {
        this.bitacoraProyecto = bitacoraProyecto;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public Long getTamanoArchivo() {
        return tamanoArchivo;
    }

    public void setTamanoArchivo(Long tamanoArchivo) {
        this.tamanoArchivo = tamanoArchivo;
    }

    public byte[] getArchivo() {
        return archivo;
    }

    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    @Override
    public String toString() {
        return "Archivo [idArchivo=" + idArchivo + ", bitacoraProyecto=" + bitacoraProyecto + ", nombreArchivo="
                + nombreArchivo + ", tamanoArchivo=" + tamanoArchivo + ", archivo=" + Arrays.toString(archivo)
                + ", fechaCreacion=" + fechaCreacion + ", usuarioCreacion=" + usuarioCreacion + "]";
    }
    
}
