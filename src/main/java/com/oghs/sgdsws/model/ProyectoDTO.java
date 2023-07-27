package com.oghs.sgdsws.model;

import java.io.Serializable;
import java.util.List;

import com.oghs.sgdsws.model.entity.BitacoraProyecto;
import com.oghs.sgdsws.model.entity.Comentario;
import com.oghs.sgdsws.model.entity.Proyecto;
import com.oghs.sgdsws.model.entity.Usuario;
import com.oghs.sgdsws.util.Paginado;

import jakarta.validation.Valid;

/**
 *
 * @author oghs
 */
public class ProyectoDTO implements Serializable {
    
    private @Valid Proyecto proyecto;
    
    private @Valid BitacoraProyecto bitacoraProyecto;
    
    private List<Usuario> listaUsuariosProyecto;
    
    private Paginado<BitacoraProyecto> listaBitacoraProyectoPaginado;

    private Comentario comentario;

    public Proyecto getProyecto() {
        return proyecto;
    }
    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }
    public BitacoraProyecto getBitacoraProyecto() {
        return bitacoraProyecto;
    }
    public void setBitacoraProyecto(BitacoraProyecto bitacoraProyecto) {
        this.bitacoraProyecto = bitacoraProyecto;
    }
    public List<Usuario> getListaUsuariosProyecto() {
        return listaUsuariosProyecto;
    }
    public void setListaUsuariosProyecto(List<Usuario> listaUsuariosProyecto) {
        this.listaUsuariosProyecto = listaUsuariosProyecto;
    }
    public Paginado<BitacoraProyecto> getListaBitacoraProyectoPaginado() {
        return listaBitacoraProyectoPaginado;
    }
    public void setListaBitacoraProyectoPaginado(Paginado<BitacoraProyecto> listaBitacoraProyectoPaginado) {
        this.listaBitacoraProyectoPaginado = listaBitacoraProyectoPaginado;
    }
    public Comentario getComentario() {
        return comentario;
    }
    public void setComentario(Comentario comentario) {
        this.comentario = comentario;
    }
    
}
