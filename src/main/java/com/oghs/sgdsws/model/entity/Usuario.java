package com.oghs.sgdsws.model.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.oghs.sgdsws.model.Estatus;

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

/**
 *
 * @author oghs
 */
@Entity
@Table(name = "USUARIO")
public class Usuario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USUARIO")
    private Long idUsuario;

    @Column(name = "NOMBRE_USUARIO")
    @NotEmpty
    private String nombreUsuario;

    @Column(name = "CONTRASENA")
    @NotEmpty
    private String contrasena;

    @Column(name = "CORREO")
    @NotEmpty
    private String correo;

    @ManyToOne
    @JoinColumn(name = "ID_ROL")
    private Rol rol;

    @Column(name = "ESTATUS")
    @NotNull
    private Estatus estatus;

    @Column(name = "FECHA_CREACION", nullable = false, updatable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @CreationTimestamp
    private Date fechaCreacion;

    @Column(name = "FECHA_MODIFICACION")
    @UpdateTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaModificacion;

    @Column(name = "FECHA_VIGENCIA")
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaVigencia;

    @OneToMany(mappedBy = "usuario")
    private Set<UsuarioProyecto> usuarioProyecto;

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Estatus getEstatus() {
        return estatus;
    }

    public void setEstatus(Estatus estatus) {
        this.estatus = estatus;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Date getFechaVigencia() {
        return fechaVigencia;
    }

    public void setFechaVigencia(Date fechaVigencia) {
        this.fechaVigencia = fechaVigencia;
    }

    public Set<UsuarioProyecto> getUsuarioProyecto() {
        return usuarioProyecto;
    }

    public void setUsuarioProyecto(Set<UsuarioProyecto> usuarioProyecto) {
        this.usuarioProyecto = usuarioProyecto;
    }

    @Override
    public String toString() {
        return "Usuario [idUsuario=" + idUsuario + ", nombreUsuario=" + nombreUsuario + ", contrasena=" + contrasena
                + ", correo=" + correo + ", rol=" + rol + ", estatus=" + estatus + ", fechaCreacion=" + fechaCreacion
                + ", fechaModificacion=" + fechaModificacion + ", fechaVigencia=" + fechaVigencia + ", usuarioProyecto="
                + usuarioProyecto + "]";
    }
    
}
