package com.oghs.sgdsws.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.oghs.sgdsws.model.Estatus;
import com.oghs.sgdsws.model.entity.Proyecto;
import com.oghs.sgdsws.model.entity.Usuario;
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

    private final BCryptPasswordEncoder passEncoder;

    private final UsuarioRepository usuarioRepository;

    private final UsuarioProyectoRepository usuarioProyectoRepository;

    public UsuarioServiceImpl(BCryptPasswordEncoder passEncoder, UsuarioRepository usuarioRepository, UsuarioProyectoRepository usuarioProyectoRepository) {
        this.passEncoder = passEncoder;
        this.usuarioRepository = usuarioRepository;
        this.usuarioProyectoRepository = usuarioProyectoRepository;
    }

    @Override
    public List<Usuario> obtenerUsuarios() {
        return usuarioRepository.findAllByEstatusOrderByNombreUsuarioAsc(Estatus.ACTIVO);
    }

    @Override
    public Paginado<Usuario> obtenerUsuariosPaginado(int numeroPagina, int tamano) {
        PageRequest pageRequest = PageRequest.of(numeroPagina - 1, tamano, Sort.by(Sort.Direction.ASC, "idUsuario"));
        Page<Usuario> usuariosPage = usuarioRepository.findAll(pageRequest);
        
        return new Paginado<>(usuariosPage, Paginando.of(usuariosPage.getTotalPages(), numeroPagina, tamano));
    }

    @Override
    public void guardarUsuario(Usuario usuario) {
        usuario.setContrasena(passEncoder.encode(usuario.getContrasena()));
        
        usuarioRepository.save(usuario);
    }

    @Override
    public Usuario buscarUsuario(Usuario usuario) {
        if (!Objects.isNull(usuario.getIdUsuario())) {
            return usuarioRepository.findById(usuario.getIdUsuario()).orElse(null);
        } else if (!Objects.isNull(usuario.getNombreUsuario())) {
            return usuarioRepository.findByNombreUsuario(usuario.getNombreUsuario());
        } else {
            return null;
        }
    }

    @Override
    public void eliminarUsuario(Usuario usuario) {
        usuarioRepository.deleteById(usuario.getIdUsuario());
    }

    @Override
    public List<Usuario> obtenerUsuariosPorProyecto(Proyecto proyecto) {
        List<Usuario> listaUsuariosProyecto = new ArrayList<>();

        usuarioProyectoRepository.findByProyecto(proyecto).forEach(usuarioProyecto -> listaUsuariosProyecto.add(usuarioProyecto.getUsuario()));
        
        return listaUsuariosProyecto;
    }
    
}
