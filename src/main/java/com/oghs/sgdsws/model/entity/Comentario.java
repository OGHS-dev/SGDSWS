package com.oghs.sgdsws.model.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 *
 * @author oghs
 */
@Entity
@Table(name = "COMENTARIO")
public class Comentario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_COMENTARIO")
    private Long idComentario;
    
    @ManyToOne
    @JoinColumn(name = "ID_BITACORA_PROYECTO")
    @JsonBackReference
    private BitacoraProyecto bitacoraProyecto;

    @Column(name = "COMENTARIO")
    private String comentario;

    public Long getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(Long idComentario) {
        this.idComentario = idComentario;
    }

    public BitacoraProyecto getBitacoraProyecto() {
        return bitacoraProyecto;
    }

    public void setBitacoraProyecto(BitacoraProyecto bitacoraProyecto) {
        this.bitacoraProyecto = bitacoraProyecto;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    @Override
    public String toString() {
        return "Comentario [idComentario=" + idComentario + ", bitacoraProyecto=" + bitacoraProyecto + ", comentario="
                + comentario + "]";
    }
    
}
