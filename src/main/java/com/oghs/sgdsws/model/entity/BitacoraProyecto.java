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
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

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
@Entity
@Table(name = "TBL_BITACORA_PROYECTO")
public class BitacoraProyecto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_BITACORA_PROYECTO")
    private Long idBitacoraProyecto;
    
    @ManyToOne
    @JoinColumn(name = "ID_PROYECTO")
    @JsonIgnoreProperties({"estadoProyecto", "usuarioProyecto", "bitacoraProyecto"})
    private Proyecto proyecto;

    @ManyToOne
    @JoinColumn(name = "ID_MODULO")
    @JsonIgnoreProperties({"estatus", "bitacoraProyecto"})
    private Modulo modulo;

    @ManyToOne
    @JoinColumn(name = "ID_HALLAZGO")
    @JsonIgnoreProperties({"estatus", "bitacoraProyecto"})
    private Hallazgo hallazgo;

    @ManyToOne
    @JoinColumn(name = "ID_INCIDENTE")
    @JsonIgnoreProperties({"estatus", "bitacoraProyecto"})
    private Incidente incidente;

    @ManyToOne
    @JoinColumn(name = "ID_CATEGORIA")
    @JsonIgnoreProperties({"estatus", "bitacoraProyecto"})
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "ID_PRIORIDAD")
    @JsonIgnoreProperties({"estatus", "bitacoraProyecto"})
    private Prioridad prioridad;

    @ManyToOne
    @JoinColumn(name = "ID_IMPACTO")
    @JsonIgnoreProperties({"estatus", "bitacoraProyecto"})
    private Impacto impacto;

    @ManyToOne
    @JoinColumn(name = "ID_NIVEL_RIESGO")
    @JsonIgnoreProperties({"estatus", "bitacoraProyecto"})
    private NivelRiesgo  nivelRiesgo;

    @ManyToOne
    @JoinColumn(name = "ID_ESTADO_BITACORA_PROYECTO")
    @JsonIgnoreProperties({"estatus", "bitacoraProyecto"})
    private EstadoBitacoraProyecto estadoBitacoraProyecto;
    
    @Column(name = "FECHA_BITACORA", nullable = false, updatable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @CreationTimestamp
    private Date fechaBitacora;

    @Column(name = "USUARIO_REPORTE")
    private String usuarioReporte;
    
    @Column(name = "REVISION")
    private Long revision;
    
    @Column(name = "DESCRIPCION")
    @NotEmpty(message = "{NotEmpty.BitacoraProyecto.descripcion}")
    @Size(min = 1, max = 200, message = "{Size.BitacoraProyecto.descripcion}")
    private String descripcion;

    @Column(name = "COMPONENTE")
    @NotEmpty(message = "{NotEmpty.BitacoraProyecto.componente}")
    @Size(min = 1, max = 20, message = "{Size.BitacoraProyecto.componente}")
    private String componente;

    @Column(name = "VERSION")
    @NotEmpty(message = "{NotEmpty.BitacoraProyecto.version}")
    @Size(min = 1, max = 20, message = "{Size.BitacoraProyecto.version}")
    private String version;

    @Column(name = "FRECUENCIA")
    // @NotEmpty(message = "{NotEmpty.BitacoraProyecto.frecuencia}")
    // @Size(min = 1, max = 20, message = "{Size.BitacoraProyecto.frecuencia}")
    private String frecuencia;

    @Column(name = "ACCIONES")
    // @NotEmpty(message = "{NotEmpty.BitacoraProyecto.acciones}")
    // @Size(min = 1, max = 200, message = "{Size.BitacoraProyecto.acciones}")
    private String acciones;

    @Column(name = "USUARIO_ASIGNADO")
    // @NotEmpty(message = "{NotEmpty.BitacoraProyecto.usuarioAsignado}")
    private String usuarioAsignado;

    @Column(name = "FECHA_ATENCION")
    // @NotNull(message = "{NotNull.BitacoraProyecto.fechaAtencion}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaAtencion;

    @OneToMany(mappedBy = "bitacoraProyecto")
    @JsonManagedReference
    private Set<Archivo> archivo;
    
    @OneToMany(mappedBy = "bitacoraProyecto")
    @JsonManagedReference
    private Set<Comentario> comentario;

    public Long getIdBitacoraProyecto() {
        return idBitacoraProyecto;
    }

    public void setIdBitacoraProyecto(Long idBitacoraProyecto) {
        this.idBitacoraProyecto = idBitacoraProyecto;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }

    public Hallazgo getHallazgo() {
        return hallazgo;
    }

    public void setHallazgo(Hallazgo hallazgo) {
        this.hallazgo = hallazgo;
    }

    public Incidente getIncidente() {
        return incidente;
    }

    public void setIncidente(Incidente incidente) {
        this.incidente = incidente;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Prioridad getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Prioridad prioridad) {
        this.prioridad = prioridad;
    }

    public Impacto getImpacto() {
        return impacto;
    }

    public void setImpacto(Impacto impacto) {
        this.impacto = impacto;
    }

    public NivelRiesgo getNivelRiesgo() {
        return nivelRiesgo;
    }

    public void setNivelRiesgo(NivelRiesgo nivelRiesgo) {
        this.nivelRiesgo = nivelRiesgo;
    }

    public EstadoBitacoraProyecto getEstadoBitacoraProyecto() {
        return estadoBitacoraProyecto;
    }

    public void setEstadoBitacoraProyecto(EstadoBitacoraProyecto estadoBitacoraProyecto) {
        this.estadoBitacoraProyecto = estadoBitacoraProyecto;
    }

    public Date getFechaBitacora() {
        return fechaBitacora;
    }

    public void setFechaBitacora(Date fechaBitacora) {
        this.fechaBitacora = fechaBitacora;
    }

    public String getUsuarioReporte() {
        return usuarioReporte;
    }

    public void setUsuarioReporte(String usuarioReporte) {
        this.usuarioReporte = usuarioReporte;
    }

    public Long getRevision() {
        return revision;
    }

    public void setRevision(Long revision) {
        this.revision = revision;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getComponente() {
        return componente;
    }

    public void setComponente(String componente) {
        this.componente = componente;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }

    public String getAcciones() {
        return acciones;
    }

    public void setAcciones(String acciones) {
        this.acciones = acciones;
    }

    public String getUsuarioAsignado() {
        return usuarioAsignado;
    }

    public void setUsuarioAsignado(String usuarioAsignado) {
        this.usuarioAsignado = usuarioAsignado;
    }

    public Date getFechaAtencion() {
        return fechaAtencion;
    }

    public void setFechaAtencion(Date fechaAtencion) {
        this.fechaAtencion = fechaAtencion;
    }

    public Set<Archivo> getArchivo() {
        return archivo;
    }

    public void setArchivo(Set<Archivo> archivo) {
        this.archivo = archivo;
    }

    public Set<Comentario> getComentario() {
        return comentario;
    }

    public void setComentario(Set<Comentario> comentario) {
        this.comentario = comentario;
    }

    @Override
    public String toString() {
        return "BitacoraProyecto [idBitacoraProyecto=" + idBitacoraProyecto + ", proyecto=" + proyecto + ", modulo="
                + modulo + ", hallazgo=" + hallazgo + ", incidente=" + incidente + ", categoria=" + categoria
                + ", prioridad=" + prioridad + ", impacto=" + impacto + ", nivelRiesgo=" + nivelRiesgo
                + ", estadoBitacoraProyecto=" + estadoBitacoraProyecto + ", fechaBitacora=" + fechaBitacora
                + ", usuarioReporte=" + usuarioReporte + ", revision=" + revision + ", descripcion=" + descripcion
                + ", componente=" + componente + ", version=" + version + ", frecuencia=" + frecuencia + ", acciones="
                + acciones + ", usuarioAsignado=" + usuarioAsignado + ", fechaAtencion=" + fechaAtencion + "]";
    }
    
}
