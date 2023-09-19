package com.oghs.sgdsws.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.oghs.sgdsws.model.Estatus;
import com.oghs.sgdsws.model.entity.Proyecto;
import com.oghs.sgdsws.model.entity.Usuario;
import com.oghs.sgdsws.model.entity.UsuarioProyecto;
import com.oghs.sgdsws.repository.UsuarioProyectoRepository;
import com.oghs.sgdsws.repository.UsuarioRepository;
import com.oghs.sgdsws.util.Paginado;
import com.oghs.sgdsws.util.Paginando;

/**
 *
 * @author oghs
 */
@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private BCryptPasswordEncoder passEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioProyectoRepository usuarioProyectoRepository;

    @Override
    public List<Usuario> obtenerUsuarios() {
        return (List<Usuario>) usuarioRepository.findAllByEstatusOrderByNombreUsuarioAsc(Estatus.Activo);
    }

    @Override
    public Paginado<Usuario> obtenerUsuariosPaginado(int numeroPagina, int tamano) {
        PageRequest pageRequest = PageRequest.of(numeroPagina - 1, tamano, Sort.by(Sort.Direction.ASC, "idUsuario"));
        Page<Usuario> usuariosPage = (Page<Usuario>) usuarioRepository.findAll(pageRequest);
        
        return new Paginado<>(usuariosPage, Paginando.of(usuariosPage.getTotalPages(), numeroPagina, tamano));
    }

    @Override
    public void guardarUsuario(Usuario usuario) {
        usuario.setContrasena(passEncoder.encode(usuario.getContrasena()));
        
        usuarioRepository.save(usuario);
    }

    @Override
    public Usuario buscarUsuario(Usuario usuario) {
        if (usuario.getIdUsuario() != null) {
            return usuarioRepository.findById(usuario.getIdUsuario()).orElse(null);
        } else {
            return usuarioRepository.findByNombreUsuario(usuario.getNombreUsuario());
        }
    }

    @Override
    public void eliminarUsuario(Usuario usuario) {
        usuarioRepository.deleteById(usuario.getIdUsuario());
    }

    @Override
    public List<Usuario> obtenerUsuariosPorProyecto(Proyecto proyecto) {
        List<Usuario> listaUsuariosProyecto = new ArrayList<>();

        for (UsuarioProyecto usuarioProyecto : usuarioProyectoRepository.findByProyecto(proyecto)) {
            listaUsuariosProyecto.add(usuarioProyecto.getUsuario());
        }
        
        return listaUsuariosProyecto;
    }
    
}
