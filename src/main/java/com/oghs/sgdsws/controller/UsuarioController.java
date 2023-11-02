package com.oghs.sgdsws.controller;

import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.oghs.sgdsws.model.entity.Usuario;
import com.oghs.sgdsws.service.BitacoraProyectoService;
import com.oghs.sgdsws.service.ProyectoService;
import com.oghs.sgdsws.service.RolService;
import com.oghs.sgdsws.service.UsuarioService;

import jakarta.validation.Valid;

/**
 * UsuarioController es la clase controlador para ejecutar las
 * operaciones CRUD de usuarios.
 * 
 * @author oghs
 * @version 1.0
 */
@Controller
@RequestMapping("/views/usuarios")
public class UsuarioController {

    private static final String RUTA_VISTA = "/views/usuarios/";

    private final UsuarioService usuarioService;

    private final RolService rolService;

    private final ProyectoService proyectoService;

    private final BitacoraProyectoService bitacoraProyectoService;
    
    public UsuarioController(UsuarioService usuarioService, RolService rolService, ProyectoService proyectoService, BitacoraProyectoService bitacoraProyectoService) {
        this.usuarioService = usuarioService;
        this.rolService = rolService;
        this.proyectoService = proyectoService;
        this.bitacoraProyectoService = bitacoraProyectoService;
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPERVISOR", "ROLE_AUDITOR", "ROLE_REVISOR"})
    @GetMapping("/")
    public String verUsuarios(@RequestParam(value = "numeroPagina", required = false, defaultValue = "1") int numeroPagina, @RequestParam(value = "tamano", required = false, defaultValue = "5") int tamano, Model model) {
        model.addAttribute("titulo", "Usuarios");
        model.addAttribute("usuarios", usuarioService.obtenerUsuariosPaginado(numeroPagina, tamano));

        return RUTA_VISTA + "verUsuarios";
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPERVISOR", "ROLE_AUDITOR"})
    @GetMapping("/crear")
    public String crearUsuario(Model model) {
        model.addAttribute("titulo", "Nuevo Usuario");
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("listaRoles", rolService.obtenerRoles());

        return RUTA_VISTA + "crearUsuario";
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPERVISOR", "ROLE_AUDITOR"})
    @PostMapping("/guardar")
    public String guardarUsuario(@Valid @ModelAttribute Usuario usuario, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        // Validar errores en el formulario
        if (bindingResult.hasErrors()) {
            model.addAttribute("titulo", "Crear/Editar Usuario");
            model.addAttribute("usuario", usuario);
            model.addAttribute("listaRoles", rolService.obtenerRoles());

            String errores = "Error en los datos proporcionados:\n\n" + bindingResult.getFieldErrors().stream().map(error -> error.getDefaultMessage() + "\n").collect(Collectors.joining());
            model.addAttribute("warning", errores);
            
            return RUTA_VISTA + "crearUsuario";
        }

        usuarioService.guardarUsuario(usuario);

        redirectAttributes.addFlashAttribute("success", String.format("Usuario: %s guardado exitosamente", usuario.getNombreUsuario()));

        return "redirect:" + RUTA_VISTA;
    }
    
    @Secured({"ROLE_ADMIN", "ROLE_SUPERVISOR"})
    @GetMapping("/editar/{idUsuario}")
    public String editarUsuario(@PathVariable("idUsuario") Long idUsuario, Model model, RedirectAttributes redirectAttributes) {
        // Validar que exista el usuario
        Usuario usuario = this.validarUsuario(idUsuario);

        if (Objects.isNull(usuario)) {
            redirectAttributes.addFlashAttribute("error", String.format("El usuario: %d no existe", idUsuario));

            return "redirect:" + RUTA_VISTA;
        }
        
        model.addAttribute("titulo", "Editar Usuario");
        model.addAttribute("usuario", usuario);
        model.addAttribute("listaRoles", rolService.obtenerRoles());

        return RUTA_VISTA + "crearUsuario";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/eliminar/{idUsuario}")
    public String eliminarUsuario(@PathVariable("idUsuario") Long idUsuario, RedirectAttributes redirectAttributes) {
        // Validar que exista el usuario
        Usuario usuario = this.validarUsuario(idUsuario);

        if (Objects.isNull(usuario)) {
            redirectAttributes.addFlashAttribute("error", String.format("El usuario: %d no existe", idUsuario));
        } else {
            if (usuario.getUsuarioProyecto().isEmpty()) {
                usuarioService.eliminarUsuario(usuario);

                redirectAttributes.addFlashAttribute("success", String.format("Usuario: %s eliminado exitosamente", usuario.getNombreUsuario()));
            } else {
                String proyectosUsuario = usuario.getUsuarioProyecto().stream().map(up -> up.getProyecto().getNombre() + ", ").collect(Collectors.joining());
                redirectAttributes.addFlashAttribute("warning", String.format("El usuario: %s no se puede eliminar ya que se encuentra en los siguientes proyectos: %s", usuario.getNombreUsuario(), proyectosUsuario));
            }
        }
        
        return "redirect:" + RUTA_VISTA;
    }

    /**
     * Retorna una vista dependiendo si el usuario ya inició sesión o no al consultar el perfil del usuario.
     *
     * @param model el objeto para mandar atributos y valores a la vista
     * @param redirectAttributes el objeto para mandar atributos flash
     * @return la cadena con la ruta de la vista (HTML) a retornar
     */
    @Secured({"ROLE_ADMIN", "ROLE_SUPERVISOR", "ROLE_AUDITOR", "ROLE_REVISOR", "ROLE_DESARROLLO"})
    @GetMapping("/perfil")
    public String perfilUsuario(Model model, RedirectAttributes redirectAttributes) {
        Usuario usuario = null;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String nombreUsuario = authentication.getName();
            
            usuario = new Usuario();
            usuario.setNombreUsuario(nombreUsuario);
            usuario = usuarioService.buscarUsuario(usuario);

            if (Objects.isNull(usuario)) {
                redirectAttributes.addFlashAttribute("success", String.format("El usuario: %s no existe", nombreUsuario));
                
                return "redirect:/home";
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "No se ha iniciado sesión");
            
            return "redirect:/home";
        }

        model.addAttribute("titulo", "Perfil Usuario");
        model.addAttribute("usuario", usuario);
        model.addAttribute("listaProyectosUsuario", proyectoService.obtenerProyectosPorUsuario(usuario));
        model.addAttribute("listaBitcoraProyectoUsuario", bitacoraProyectoService.buscarBitacoraProyectoPorUsuarioAsignado(authentication.getName()));

        return RUTA_VISTA + "perfilUsuario";
    }

    /**
     * Retorna un objeto Usuario si éste existe y se encontró.
     *
     * @param idUsuario el id del usuario a buscar
     * @return usuario el objeto del usuario encontrado
     */
    private Usuario validarUsuario(Long idUsuario) {
        Usuario usuario = null;

        if (idUsuario > 0) {
            usuario = new Usuario();
            usuario.setIdUsuario(idUsuario);
            usuario = usuarioService.buscarUsuario(usuario);

            // No se encontró el usuario
            if (Objects.isNull(usuario)) {
                return null;
            }
        }

        return usuario;
    }
}
