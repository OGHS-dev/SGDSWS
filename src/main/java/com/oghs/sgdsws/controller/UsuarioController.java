package com.oghs.sgdsws.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.oghs.sgdsws.model.entity.Usuario;
import com.oghs.sgdsws.model.service.RolService;
import com.oghs.sgdsws.model.service.UsuarioService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;


/**
 *
 * @author oghs
 */
@Controller
@RequestMapping("/views/usuarios")
public class UsuarioController {

    private final String RUTA_VISTA = "/views/usuarios/";

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolService rolService;
    
    @Secured("ROLE_ADMIN")
    @GetMapping("/")
    public String verUsuarios(@RequestParam(value = "numeroPagina", required = false, defaultValue = "1") int numeroPagina, @RequestParam(value = "tamano", required = false, defaultValue = "5") int tamano, Model model) {
        model.addAttribute("titulo", "Usuarios");
        model.addAttribute("usuarios", usuarioService.obtenerUsuariosPaginado(numeroPagina, tamano));

        return RUTA_VISTA + "verUsuarios";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/crear")
    public String crearUsuario(Model model) {
        model.addAttribute("titulo", "Nuevo Usuario");
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("listaRoles", rolService.obtenerRoles());

        return RUTA_VISTA + "crearUsuario";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/guardar")
    public String guardarUsuario(@Valid @ModelAttribute Usuario usuario, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        // Validar errores en el formulario
        if (bindingResult.hasErrors()) {
            model.addAttribute("titulo", "Crear/Editar Usuario");
            model.addAttribute("usuario", usuario);
            model.addAttribute("listaRoles", rolService.obtenerRoles());

            System.err.println("Error en los datos proporcionados");
            
            return RUTA_VISTA + "crearUsuario";
        }

        usuarioService.guardarUsuario(usuario);

        redirectAttributes.addFlashAttribute("success", "Usuario: " + usuario.getNombreUsuario() + " guardado exitosamente");

        return "redirect:" + RUTA_VISTA;
    }
    
    @Secured("ROLE_ADMIN")
    @GetMapping("/editar/{idUsuario}")
    public String editarUsuario(@PathVariable("idUsuario") Long idUsuario, Model model, RedirectAttributes redirectAttributes) {
        Usuario usuario = null;

        // Validar que exista el usuario
        if (idUsuario > 0) {
            usuario = new Usuario();
            usuario.setIdUsuario(idUsuario);
            usuario = usuarioService.buscarUsuario(usuario);

            if (usuario == null) {
                redirectAttributes.addFlashAttribute("error", "El usuario: " + idUsuario + " no existe");
                
                return "redirect:" + RUTA_VISTA;
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "No se encontró el usuario: " + idUsuario);
            
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
        Usuario usuario = null;

        // Validar que exista el usuario
        if (idUsuario > 0) {
            usuario = new Usuario();
            usuario.setIdUsuario(idUsuario);
            usuario = usuarioService.buscarUsuario(usuario);

            if (usuario == null) {
                redirectAttributes.addFlashAttribute("error", "El usuario: " + idUsuario + " no existe");
                
                return "redirect:" + RUTA_VISTA;
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "No se encontró el usuario: " + idUsuario);
            
            return "redirect:" + RUTA_VISTA;
        }

        usuarioService.eliminarUsuario(usuario);

        redirectAttributes.addFlashAttribute("success", "Usuario: " + usuario.getNombreUsuario() + " eliminado exitosamente");
        
        return "redirect:" + RUTA_VISTA;
    }
}
