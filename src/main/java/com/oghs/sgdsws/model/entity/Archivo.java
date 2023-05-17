package com.oghs.sgdsws.model.entity;

import java.io.Serializable;
import java.sql.Blob;

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
@Table(name = "ARCHIVO")
public class Archivo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ARCHIVO")
    private Long idArchivo;
    
    @ManyToOne
    @JoinColumn(name = "ID_BITACORA_PROYECTO")
    private BitacoraProyecto bitacoraProyecto;

    @Lob
    @Column(name = "ARCHIVO")
    private Blob archivo;

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

    public Blob getArchivo() {
        return archivo;
    }

    public void setArchivo(Blob archivo) {
        this.archivo = archivo;
    }

    @Override
    public String toString() {
        return "Archivo [idArchivo=" + idArchivo + ", bitacoraProyecto=" + bitacoraProyecto + ", archivo=" + archivo
                + "]";
    }
    
}
