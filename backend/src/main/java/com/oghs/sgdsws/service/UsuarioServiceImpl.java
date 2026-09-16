package com.oghs.sgdsws.service;

import java.util.List;
import java.util.Objects;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.oghs.sgdsws.model.Estatus;
import com.oghs.sgdsws.model.entity.Usuario;
import com.oghs.sgdsws.repository.RolRepository;
import com.oghs.sgdsws.repository.UsuarioRepository;
import com.oghs.sgdsws.exceptionhandler.DuplicateResourceException;

import lombok.RequiredArgsConstructor;

/**
 *
 * @author oghs
 */
@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final BCryptPasswordEncoder passEncoder;

    private final UsuarioRepository usuarioRepository;

    private final RolRepository rolRepository;

    // private final UsuarioProyectoRepository usuarioProyectoRepository;

    // public UsuarioServiceImpl(BCryptPasswordEncoder passEncoder, UsuarioRepository usuarioRepository, RolRepository rolRepository/*, UsuarioProyectoRepository usuarioProyectoRepository*/) {
    //     this.passEncoder = passEncoder;
    //     this.usuarioRepository = usuarioRepository;
    //     this.rolRepository = rolRepository;
    //     // this.usuarioProyectoRepository = usuarioProyectoRepository;
    // }

    @Override
    public List<Usuario> obtenerUsuarios() {
        return usuarioRepository.findAllByEstatusOrderByNombreUsuarioAsc(Estatus.ACTIVO);
    }

    // @Override
    // public Paginado<Usuario> obtenerUsuariosPaginado(int numeroPagina, int tamano) {
    //     PageRequest pageRequest = PageRequest.of(numeroPagina - 1, tamano, Sort.by(Sort.Direction.ASC, "idUsuario"));
    //     Page<Usuario> usuariosPage = usuarioRepository.findAll(pageRequest);
        
    //     return new Paginado<>(usuariosPage, Paginando.of(usuariosPage.getTotalPages(), numeroPagina, tamano));
    // }

    @Override
    public Usuario guardarUsuario(Usuario usuario) {
        Usuario usuarioExistente = usuarioRepository.findByNombreUsuario(usuario.getNombreUsuario()).orElse(null);
        if (Objects.nonNull(usuarioExistente)) {
            throw new DuplicateResourceException("El nombre de usuario ya existe.");
        }

        usuario.setContrasena(passEncoder.encode(usuario.getContrasena()));
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario buscarUsuario(Usuario usuario) {
        if (Objects.nonNull(usuario.getIdUsuario())) {
            return usuarioRepository.findById(usuario.getIdUsuario()).orElse(null);
        } else if (Objects.nonNull(usuario.getNombreUsuario())) {
            return usuarioRepository.findByNombreUsuario(usuario.getNombreUsuario()).orElse(null);
        } else {
            return null;
        }
    }

    @Override
    public void eliminarUsuario(Usuario usuario) {
        if (Objects.nonNull(usuario.getIdUsuario())) {
            usuarioRepository.deleteById(usuario.getIdUsuario());
        }
    }

    // @Override
    // public List<Usuario> obtenerUsuariosPorProyecto(Proyecto proyecto) {
    //     List<Usuario> listaUsuariosProyecto = new ArrayList<>();

    //     usuarioProyectoRepository.findByProyecto(proyecto).forEach(usuarioProyecto -> listaUsuariosProyecto.add(usuarioProyecto.getUsuario()));
        
    //     return listaUsuariosProyecto;
    // }
    
}
