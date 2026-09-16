package com.oghs.sgdsws.model.entity;

import java.io.Serializable;
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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author oghs
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TBL_COMENTARIO")
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

    @Column(name = "FECHA_CREACION", nullable = false, updatable = false)
    // @DateTimeFormat(pattern = "yyyy-MM-dd")
    @CreationTimestamp
    private Date fechaCreacion;

    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;
    
}
