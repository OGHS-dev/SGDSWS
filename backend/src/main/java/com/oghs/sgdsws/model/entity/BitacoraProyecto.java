package com.oghs.sgdsws.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 *
 * @author oghs
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TBL_BITACORA_PROYECTO")
public class BitacoraProyecto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_BITACORA_PROYECTO")
    private Long idBitacoraProyecto;
    
    @ManyToOne
    // @JoinColumn(name = "ID_PROYECTO")
    @JsonIgnoreProperties({"estadoProyecto", "usuarioProyecto", "bitacoraProyecto"})
    private Proyecto proyecto;

    @Column(name = "FECHA_BITACORA", nullable = false, updatable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @CreationTimestamp
    private Date fechaBitacora;

    @Column(name = "USUARIO_REPORTE")
    private String usuarioReporte;
    
    @Column(name = "REVISION")
    private Long revision;
    
    @Column(name = "DESCRIPCION")
    private String descripcion;

    @Column(name = "COMPONENTE")
    private String componente;

    @Column(name = "VERSION")
    private String version;

    @Column(name = "FRECUENCIA")
    private String frecuencia;

    @ManyToOne
    // @JoinColumn(name = "ID_MODULO")
    @JsonIgnoreProperties({"estatus", "bitacoraProyecto"})
    private Modulo modulo;

    @ManyToOne
    // @JoinColumn(name = "ID_HALLAZGO")
    @JsonIgnoreProperties({"estatus", "bitacoraProyecto"})
    private Hallazgo hallazgo;

    @ManyToOne
    // @JoinColumn(name = "ID_INCIDENTE")
    @JsonIgnoreProperties({"estatus", "bitacoraProyecto"})
    private Incidente incidente;

    @ManyToOne
    // @JoinColumn(name = "ID_CATEGORIA")
    @JsonIgnoreProperties({"estatus", "bitacoraProyecto"})
    private Categoria categoria;

    @ManyToOne
    // @JoinColumn(name = "ID_PRIORIDAD")
    @JsonIgnoreProperties({"estatus", "bitacoraProyecto"})
    private Prioridad prioridad;

    @ManyToOne
    // @JoinColumn(name = "ID_IMPACTO")
    @JsonIgnoreProperties({"estatus", "bitacoraProyecto"})
    private Impacto impacto;

    @ManyToOne
    // @JoinColumn(name = "ID_NIVEL_RIESGO")
    @JsonIgnoreProperties({"estatus", "bitacoraProyecto"})
    private NivelRiesgo  nivelRiesgo;

    @ManyToOne
    // @JoinColumn(name = "ID_ESTADO_BITACORA_PROYECTO")
    @JsonIgnoreProperties({"estatus", "bitacoraProyecto"})
    private EstadoBitacoraProyecto estadoBitacoraProyecto;

    @Column(name = "ACCIONES")
    private String acciones;

    @Column(name = "USUARIO_ASIGNADO")
    private String usuarioAsignado;

    @Column(name = "FECHA_ATENCION")
    // @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaAtencion;

    @OneToMany(mappedBy = "bitacoraProyecto")
    @JsonManagedReference
    private Set<Archivo> archivo;
    
    @OneToMany(mappedBy = "bitacoraProyecto")
    @JsonManagedReference
    private Set<Comentario> comentario;
    
}
