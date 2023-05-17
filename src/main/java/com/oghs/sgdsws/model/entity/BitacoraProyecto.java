package com.oghs.sgdsws.model.entity;

import com.oghs.sgdsws.model.EstatusBitacoraProyecto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author oghs
 */
@Entity
@Table(name = "BITACORA_PROYECTO")
public class BitacoraProyecto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_BITACORA_PROYECTO")
    private Long idBitacoraProyecto;
    
    @ManyToOne
    @JoinColumn(name = "ID_PROYECTO")
    private Proyecto proyecto;
    
    @Column(name = "FECHA_BITACORA", nullable = false, updatable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @CreationTimestamp
    private Date fechaBitacora;
    
    @Column(name = "REVISION")
    @NotNull
    private Long revision;
    
    @Column(name = "ESTATUS")
    @NotNull
    private EstatusBitacoraProyecto estatus;
    
    @OneToMany(mappedBy = "bitacoraProyecto")
    private Set<Archivo> archivo;
    
    @OneToMany(mappedBy = "bitacoraProyecto")
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

    public Date getFechaBitacora() {
        return fechaBitacora;
    }

    public void setFechaBitacora(Date fechaBitacora) {
        this.fechaBitacora = fechaBitacora;
    }

    public Long getRevision() {
        return revision;
    }

    public void setRevision(Long revision) {
        this.revision = revision;
    }

    public EstatusBitacoraProyecto getEstatus() {
        return estatus;
    }

    public void setEstatus(EstatusBitacoraProyecto estatus) {
        this.estatus = estatus;
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
        return "BitacoraProyecto [idBitacoraProyecto=" + idBitacoraProyecto + ", proyecto=" + proyecto
                + ", fechaBitacora=" + fechaBitacora + ", revision=" + revision + ", estatus=" + estatus + ", archivo="
                + archivo + ", comentario=" + comentario + "]";
    }
    
}
