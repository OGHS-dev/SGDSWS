package com.oghs.sgdsws.model.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

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
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 *
 * @author oghs
 */
@Entity
@Table(name = "USUARIO")
public class Usuario extends Auditable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USUARIO")
    private Long idUsuario;

    @Column(name = "NOMBRE_USUARIO")
    @NotEmpty(message = "{NotEmpty.Usuario.nombreUsuario}")
    @Size(min = 1, max = 20, message = "{Size.Usuario.nombreUsuario}")
    private String nombreUsuario;

    @Column(name = "CONTRASENA")
    @NotEmpty(message = "{NotEmpty.Usuario.contrasena}")
    @Size(min = 1, max = 100, message = "{Size.Usuario.contrasena}")
    private String contrasena;

    @Column(name = "CORREO")
    @NotEmpty(message = "{NotEmpty.Usuario.correo}")
    @Email(message = "{Email.Usuario.correo}")
    private String correo;

    @ManyToOne
    @JoinColumn(name = "ID_ROL")
    private Rol rol;

    @Column(name = "ESTATUS")
    @NotNull(message = "{NotNull.Usuario.estatus}")
    private Estatus estatus;

    @Column(name = "FECHA_VIGENCIA")
    @NotNull(message = "{NotNull.Usuario.fechaVigencia}")
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
                + ", correo=" + correo + ", estatus=" + estatus + ", fechaVigencia=" + fechaVigencia
                + ", usuarioProyecto=" + usuarioProyecto + "]";
    }

}
