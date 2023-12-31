package com.oghs.sgdsws.controller;

import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.access.annotation.Secured;
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

import com.oghs.sgdsws.model.entity.Rol;
import com.oghs.sgdsws.service.RolService;

import jakarta.validation.Valid;

/**
 * RolController es la clase controlador para ejecutar las
 * operaciones CRUD de roles.
 * 
 * @author oghs
 * @version 1.0
 */
@Controller
@RequestMapping("/views/roles")
public class RolController {

    private static final String RUTA_VISTA = "/views/roles/";
    
    private final RolService rolService;

    public RolController(RolService rolService) {
        this.rolService = rolService;
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPERVISOR", "ROLE_AUDITOR", "ROLE_REVISOR"})
    @GetMapping("/")
    public String verRoles(@RequestParam(value = "numeroPagina", required = false, defaultValue = "1") int numeroPagina, @RequestParam(value = "tamano", required = false, defaultValue = "5") int tamano, Model model) {
        model.addAttribute("titulo", "Roles");
        model.addAttribute("roles", rolService.obtenerRolesPaginado(numeroPagina, tamano));

        return RUTA_VISTA + "verRoles";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/crear")
    public String crearRol(Model model) {
        model.addAttribute("titulo", "Nuevo Rol");
        model.addAttribute("rol", new Rol());

        return RUTA_VISTA + "crearRol";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/guardar")
    public String guardarRol(@Valid @ModelAttribute Rol rol, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        // Validar errores en el formulario
        if (bindingResult.hasErrors()) {
            model.addAttribute("titulo", "Crear/Editar Rol");
            model.addAttribute("rol", rol);

            String errores = "Error en los datos proporcionados:\n\n" + bindingResult.getFieldErrors().stream().map(error -> error.getDefaultMessage() + "\n").collect(Collectors.joining());
            model.addAttribute("warning", errores);
            
            return RUTA_VISTA + "crearRol";
        }

        rolService.guardarRol(rol);

        redirectAttributes.addFlashAttribute("success", String.format("Rol: %s guardado exitosamente", rol.getCodigo()));

        return "redirect:" + RUTA_VISTA;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/editar/{idRol}")
    public String editarRol(@PathVariable("idRol") Long idRol, Model model, RedirectAttributes redirectAttributes) {
        // Validar que exista el rol
        Rol rol = this.validaRol(idRol);

        if (Objects.isNull(rol)) {
            redirectAttributes.addFlashAttribute("error", String.format("El rol: %d no existe", idRol));
            
            return "redirect:" + RUTA_VISTA;
        }

        model.addAttribute("titulo", "Editar Rol");
        model.addAttribute("rol", rol);

        return RUTA_VISTA + "crearRol";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/eliminar/{idRol}")
    public String eliminarRol(@PathVariable("idRol") Long idRol, RedirectAttributes redirectAttributes) {
        // Validar que exista el rol
        Rol rol = this.validaRol(idRol);

        if (Objects.isNull(rol)) {
            redirectAttributes.addFlashAttribute("error", String.format("El rol: %d no existe", idRol));
        } else {
            if (rol.getUsuario().isEmpty()) {
                rolService.eliminarRol(rol);

                redirectAttributes.addFlashAttribute("success", String.format("Rol: %s eliminado exitosamente", rol.getCodigo()));
            } else {
                String usuarios = rol.getUsuario().stream().map(u -> u.getNombreUsuario() + ", ").collect(Collectors.joining());
                redirectAttributes.addFlashAttribute("warning", String.format("El rol: %s no se puede eliminar ya que se encuentra asociado a los siguientes usuarios: %s", rol.getCodigo(), usuarios));
            }
        }
        
        return "redirect:" + RUTA_VISTA;
    }

    /**
     * Retorna un objeto Rol si éste existe y se encontró.
     *
     * @param idRol el id del rol a buscar
     * @return rol el objeto del rol encontrado
     */
    private Rol validaRol(Long idRol) {
        Rol rol = null;

        if (idRol > 0) {
            rol = new Rol();
            rol.setIdRol(idRol);
            rol = rolService.buscarRol(rol);

            // No se encontró el rol
            if (Objects.isNull(rol)) {
                return null;
            }
        }

        return rol;
    }
}
