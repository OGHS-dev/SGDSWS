package com.oghs.sgdsws.controller;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oghs.sgdsws.dto.request.InicioSesionRequest;
import com.oghs.sgdsws.dto.request.RegistroRequest;
import com.oghs.sgdsws.dto.response.AuthResponse;
import com.oghs.sgdsws.dto.response.UsuarioResponse;
import com.oghs.sgdsws.application.port.in.AuthenticateUserUseCase;
import com.oghs.sgdsws.application.port.in.command.AuthenticateUserCommand;
import com.oghs.sgdsws.model.entity.Rol;
import com.oghs.sgdsws.model.entity.Usuario;
import com.oghs.sgdsws.service.RolService;
import com.oghs.sgdsws.service.UsuarioService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AutenticacionController {
    private final AuthenticateUserUseCase authenticateUserUseCase;
    private final UsuarioService usuarioService;
    private final RolService rolService;

    @PostMapping(value = "registro")
    public ResponseEntity<UsuarioResponse> registro(@Valid @RequestBody RegistroRequest request) {
        Usuario usuario = Usuario.builder()
                .nombreUsuario(request.getNombreUsuario())
                .contrasena(request.getContrasena())
                .correo(request.getCorreo())
                // .usuarioRol(new HashSet<>())
                .build();

        // Un usuario que se registra se le asignara el rol por defecto sin rol
        Rol rol = Rol.builder()
                .idRol(2L)
                .build();
        rol = rolService.buscarRol(rol);

        Set<Rol> roles = new HashSet<>();
        roles.add(rol);
        
        usuario.setRoles(roles);
        usuario = usuarioService.guardarUsuario(usuario);

        return ResponseEntity.status(201).body(toResponse(usuario));
    }

    @PostMapping(value = "inicioSesion")
    public ResponseEntity<AuthResponse> inicioSesion(@Valid @RequestBody InicioSesionRequest request) {
        var result = authenticateUserUseCase.authenticate(
                new AuthenticateUserCommand(request.getNombreUsuario(), request.getContrasena()));
        return ResponseEntity.ok(AuthResponse.builder()
                .token(result.token())
                .expiraEn(result.expiresIn())
                .build());
    }

    @GetMapping("/usuarioEnSesion")
    public ResponseEntity<UsuarioResponse> usuarioEnSesion(Principal principal) {
        Usuario usuario = Usuario.builder()
                .nombreUsuario(principal.getName())
                .build();

        Usuario usuarioEnSesion = usuarioService.buscarUsuario(usuario);
        return ResponseEntity.ok(toResponse(usuarioEnSesion));
    }

    private UsuarioResponse toResponse(Usuario usuario) {
        return UsuarioResponse.builder()
                .idUsuario(usuario.getIdUsuario())
                .nombreUsuario(usuario.getNombreUsuario())
                .correo(usuario.getCorreo())
                .roles(usuario.getRoles().stream().map(Rol::getCodigo).collect(java.util.stream.Collectors.toSet()))
                .build();
    }
    
}
