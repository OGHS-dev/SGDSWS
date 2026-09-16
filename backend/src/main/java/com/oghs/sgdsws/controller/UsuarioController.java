package com.oghs.sgdsws.controller;

import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;

import com.oghs.sgdsws.model.entity.Rol;
import com.oghs.sgdsws.model.entity.Usuario;
import com.oghs.sgdsws.dto.response.UsuarioResponse;
import com.oghs.sgdsws.application.port.in.ListUsersUseCase;
import com.oghs.sgdsws.service.RolService;
import com.oghs.sgdsws.service.UsuarioService;

import lombok.RequiredArgsConstructor;

// import jakarta.validation.Valid;

/**
 * UsuarioController es la clase controlador para ejecutar las
 * operaciones CRUD de usuarios.
 * 
 * @author oghs
 * @version 1.0
 */
@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    // private static final String RUTA_VISTA = "/views/usuarios/";

    private final UsuarioService usuarioService;

    private final RolService rolService;
    private final ListUsersUseCase listUsersUseCase;

    // private final ProyectoService proyectoService;

    // private final BitacoraProyectoService bitacoraProyectoService;
    
    // public UsuarioController(UsuarioService usuarioService, RolService rolService, ProyectoService proyectoService, BitacoraProyectoService bitacoraProyectoService) {
    //     this.usuarioService = usuarioService;
    //     this.rolService = rolService;
    //     this.proyectoService = proyectoService;
    //     this.bitacoraProyectoService = bitacoraProyectoService;
    // }

    @GetMapping("/buscar/{idUsuario}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR', 'AUDITOR', 'REVISOR')")
    public ResponseEntity<UsuarioResponse> buscarUsuario(@PathVariable("idUsuario") Long idUsuario) {
        // Validar que exista el usuario
        Usuario usuario = this.validarUsuario(idUsuario);

        if (Objects.isNull(usuario)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(toResponse(usuario));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR', 'AUDITOR', 'REVISOR')")
    public ResponseEntity<java.util.List<UsuarioResponse>> listarUsuarios() {
        return ResponseEntity.ok(listUsersUseCase.listActiveUsers());
    }

    // @Secured({"ROLE_ADMIN", "ROLE_SUPERVISOR", "ROLE_AUDITOR", "ROLE_REVISOR"})
    // @GetMapping("/")
    // public String verUsuarios(@RequestParam(value = "numeroPagina", required = false, defaultValue = "1") int numeroPagina, @RequestParam(value = "tamano", required = false, defaultValue = "5") int tamano, Model model) {
    //     model.addAttribute("titulo", "Usuarios");
    //     // model.addAttribute("usuarios", usuarioService.obtenerUsuariosPaginado(numeroPagina, tamano));

    //     return RUTA_VISTA + "verUsuarios";
    // }

    // @Secured({"ROLE_ADMIN", "ROLE_SUPERVISOR", "ROLE_AUDITOR"})
    // @GetMapping("/crear")
    // public String crearUsuario(Model model) {
    //     model.addAttribute("titulo", "Nuevo Usuario");
    //     model.addAttribute("usuario", new Usuario());
    //     model.addAttribute("listaRoles", rolService.obtenerRoles());

    //     return RUTA_VISTA + "crearUsuario";
    // }

    @PostMapping("/guardar")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR', 'AUDITOR')")
    public ResponseEntity<UsuarioResponse> guardarUsuario(@Valid @RequestBody Usuario usuario) {
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
    
    // @PutMapping
    // public ResponseEntity<UsuarioResponse> editarUsuario(@RequestBody UsuarioRequest usuarioRequest) {
    //     // Validar que exista el usuario
    //     Usuario usuario = this.validarUsuario(usuarioRequest.getIdUsuario());

    //     if (Objects.isNull(usuario)) {
    //         return ResponseEntity.notFound().build();
    //     }

    //     // usuarioService.guardarUsuario(usuario);
        
    //     return ResponseEntity.ok(new UsuarioResponse("Usuario actualizado correctamente."));
    // }

    @DeleteMapping("/eliminar/{idUsuario}")
    @PreAuthorize("hasRole('ADMIN')")
    public void eliminarUsuario(@PathVariable("idUsuario") Long idUsuario) {
        // Validar que exista el usuario
        Usuario usuario = this.validarUsuario(idUsuario);

        if (Objects.isNull(usuario)) {
            // redirectAttributes.addFlashAttribute("error", String.format("El usuario: %d no existe", idUsuario));
        } else {
            // if (usuario.getUsuarioProyecto().isEmpty()) {
                usuarioService.eliminarUsuario(usuario);

                // redirectAttributes.addFlashAttribute("success", String.format("Usuario: %s eliminado exitosamente", usuario.getNombreUsuario()));
            // } else {
                // String proyectosUsuario = usuario.getUsuarioProyecto().stream().map(up -> up.getProyecto().getNombre() + ", ").collect(Collectors.joining());
                // redirectAttributes.addFlashAttribute("warning", String.format("El usuario: %s no se puede eliminar ya que se encuentra en los siguientes proyectos: %s", usuario.getNombreUsuario(), proyectosUsuario));
            // }
        }
    }

    private UsuarioResponse toResponse(Usuario usuario) {
        return UsuarioResponse.builder()
                .idUsuario(usuario.getIdUsuario())
                .nombreUsuario(usuario.getNombreUsuario())
                .correo(usuario.getCorreo())
                .roles(usuario.getRoles().stream().map(Rol::getCodigo).collect(java.util.stream.Collectors.toSet()))
                .build();
    }

    /**
     * Retorna una vista dependiendo si el usuario ya inició sesión o no al consultar el perfil del usuario.
     *
     * @param model el objeto para mandar atributos y valores a la vista
     * @param redirectAttributes el objeto para mandar atributos flash
     * @return la cadena con la ruta de la vista (HTML) a retornar
     */
    // @Secured({"ROLE_ADMIN", "ROLE_SUPERVISOR", "ROLE_AUDITOR", "ROLE_REVISOR", "ROLE_DESARROLLO"})
    // @GetMapping("/perfil")
    // public String perfilUsuario(Model model, RedirectAttributes redirectAttributes) {
    //     Usuario usuario = null;

    //     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    //     if (!(authentication instanceof AnonymousAuthenticationToken)) {
    //         String nombreUsuario = authentication.getName();
            
    //         usuario = new Usuario();
    //         usuario.setNombreUsuario(nombreUsuario);
    //         usuario = usuarioService.buscarUsuario(usuario);

    //         if (Objects.isNull(usuario)) {
    //             redirectAttributes.addFlashAttribute("success", String.format("El usuario: %s no existe", nombreUsuario));
                
    //             return "redirect:/home";
    //         }
    //     } else {
    //         redirectAttributes.addFlashAttribute("error", "No se ha iniciado sesión");
            
    //         return "redirect:/home";
    //     }

    //     model.addAttribute("titulo", "Perfil Usuario");
    //     model.addAttribute("usuario", usuario);
    //     model.addAttribute("listaProyectosUsuario", proyectoService.obtenerProyectosPorUsuario(usuario));
    //     model.addAttribute("listaBitcoraProyectoUsuario", bitacoraProyectoService.buscarBitacoraProyectoPorUsuarioAsignado(authentication.getName()));
        
    //     if (Utilerias.validarFecha(usuario.getFechaVigencia()) == 0) {
    //         model.addAttribute("warning", "La fecha de vigencia de su usuario expiró y se ha desactivado, favor de contactar a su administrador para reactivarlo");
    //     } else if (Utilerias.validarFecha(usuario.getFechaVigencia()) == 1) {
    //         model.addAttribute("info", "La fecha de vigencia de su usuario está por expirar");
    //     }

    //     return RUTA_VISTA + "perfilUsuario";
    // }

    /**
     * Retorna un objeto Usuario si éste existe y se encontró.
     *
     * @param idUsuario el id del usuario a buscar
     * @return usuario el objeto del usuario encontrado
     */
    private Usuario validarUsuario(Long idUsuario) {
        Usuario usuario = null;

        if (idUsuario > 0) {
            // usuario = new Usuario();
            // usuario.setIdUsuario(idUsuario);
            // usuario = usuarioService.buscarUsuario(usuario);
            usuario = Usuario.builder().idUsuario(idUsuario).build();
            usuario = usuarioService.buscarUsuario(usuario);

            // No se encontró el usuario
            if (Objects.isNull(usuario)) {
                return null;
            }
        }

        return usuario;
    }
}
