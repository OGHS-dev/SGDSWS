package com.oghs.sgdsws.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.oghs.sgdsws.model.service.RolService;

import jakarta.validation.Valid;

/**
 *
 * @author oghs
 */
@Controller
@RequestMapping("/views/roles")
public class RolController {
    
    @Autowired
    private RolService rolService;

    @Secured("ROLE_ADMIN")
    @GetMapping("/")
    public String verRoles(@RequestParam(value = "numeroPagina", required = false, defaultValue = "1") int numeroPagina, @RequestParam(value = "tamano", required = false, defaultValue = "5") int tamano, Model model) {
        model.addAttribute("titulo", "Roles");
        model.addAttribute("roles", rolService.obtenerRolesPaginado(numeroPagina, tamano));

        return "/views/roles/verRoles";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/crear")
    public String crearRol(Model model) {
        model.addAttribute("titulo", "Nuevo Rol");
        model.addAttribute("rol", new Rol());

        return "/views/roles/crearRol";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/guardar")
    public String guardarRol(@Valid @ModelAttribute Rol rol, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        // Validar errores en el formulario
        if (bindingResult.hasErrors()) {
            model.addAttribute("titulo", "Editar Rol");
            model.addAttribute("rol", rol);

            System.err.println("Error en los datos proporcionados");
            return "/views/roles/crearRol";
        }

        rolService.guardarRol(rol);

        redirectAttributes.addFlashAttribute("success", "Rol: " + rol.getIdRol() + " guardado exitosamente");
        return "redirect:/views/roles/";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/editar/{idRol}")
    public String editarRol(@PathVariable("idRol") Long idRol, Model model, RedirectAttributes redirectAttributes) {
        Rol rol = null;

        // Validar que exista el rol
        if (idRol > 0) {
            rol = new Rol();
            rol.setIdRol(idRol);
            rol = rolService.buscarRol(rol);

            if (rol == null) {
                redirectAttributes.addFlashAttribute("error", "El rol: " + idRol + " no existe");
                return "redirect:/views/roles/";
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "No se encontró el rol: " + idRol);
            return "redirect:/views/roles/";
        }

        model.addAttribute("titulo", "Editar Rol");
        model.addAttribute("rol", rol);

        return "/views/roles/crearRol";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/eliminar/{idRol}")
    public String eliminarRol(@PathVariable("idRol") Long idRol, RedirectAttributes redirectAttributes) {
        Rol rol = null;

        // Validar que exista el rol
        if (idRol > 0) {
            rol = new Rol();
            rol.setIdRol(idRol);
            rol = rolService.buscarRol(rol);

            if (rol == null) {
                redirectAttributes.addFlashAttribute("error", "El rol: " + idRol + " no existe");
                return "redirect:/views/roles/";
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "No se encontró el rol: " + idRol);
            return "redirect:/views/roles/";
        }

        rolService.eliminarRol(rol);

        redirectAttributes.addFlashAttribute("success", "Rol: " + rol.getIdRol() + " eliminado exitosamente");
        return "redirect:/views/roles/";
    }
}
