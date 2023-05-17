package com.oghs.sgdsws.model;

import java.io.Serializable;
import java.util.List;

import com.oghs.sgdsws.model.entity.BitacoraProyecto;
import com.oghs.sgdsws.model.entity.Proyecto;
import com.oghs.sgdsws.model.entity.Usuario;
import com.oghs.sgdsws.util.Paginado;

/**
 *
 * @author oghs
 */
public class ProyectoDTO implements Serializable {
    private Proyecto proyecto;
    private List<Usuario> listaUsuariosProyecto;
    private Paginado<BitacoraProyecto> listaBitacoraProyectoPaginado;

    public Proyecto getProyecto() {
        return proyecto;
    }
    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
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
    
}