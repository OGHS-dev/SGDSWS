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

import com.oghs.sgdsws.model.entity.EstadoProyecto;
import com.oghs.sgdsws.model.service.EstadoProyectoService;

import jakarta.validation.Valid;

/**
 *
 * @author oghs
 */
@Controller
@RequestMapping("/views/estadosProyecto")
public class EstadoProyectoController {
    
    @Autowired
    private EstadoProyectoService estadoProyectoService;

    @Secured("ROLE_ADMIN")
    @GetMapping("/")
    public String verEstadosProyecto(@RequestParam(value = "numeroPagina", required = false, defaultValue = "1") int numeroPagina, @RequestParam(value = "tamano", required = false, defaultValue = "5") int tamano, Model model) {
        model.addAttribute("titulo", "Estados Proyecto");
        model.addAttribute("estadosProyecto", estadoProyectoService.obtenerEstadosProyectoPaginado(numeroPagina, tamano));

        return "/views/estadosProyecto/verEstadosProyecto";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/crear")
    public String crearEstadoProyecto(Model model) {
        model.addAttribute("titulo", "Nuevo Estado Proyecto");
        model.addAttribute("estadoProyecto", new EstadoProyecto());

        return "/views/estadosProyecto/crearEstadoProyecto";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/guardar")
    public String guardarEstadoProyecto(@Valid @ModelAttribute EstadoProyecto estadoProyecto, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        // Validar errores en el formulario
        if (bindingResult.hasErrors()) {
            model.addAttribute("titulo", "Editar Estado Proyecto");
            model.addAttribute("estadoProyecto", estadoProyecto);

            System.err.println("Error en los datos proporcionados");
            return "/views/estadosProyecto/crearEstadoProyecto";
        }

        estadoProyectoService.guardarEstadoProyecto(estadoProyecto);

        redirectAttributes.addFlashAttribute("success", "Estado Proyecto: " + estadoProyecto.getIdEstadoProyecto() + " guardado exitosamente");
        return "redirect:/views/estadosProyecto/";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/editar/{idEstadoProyecto}")
    public String editarEstadoProyecto(@PathVariable("idEstadoProyecto") Long idEstadoProyecto, Model model, RedirectAttributes redirectAttributes) {
        EstadoProyecto estadoProyecto = null;

        // Validar que exista el estado proyecto
        if (idEstadoProyecto > 0) {
            estadoProyecto = new EstadoProyecto();
            estadoProyecto.setIdEstadoProyecto(idEstadoProyecto);
            estadoProyecto = estadoProyectoService.buscarEstadoProyecto(estadoProyecto);

            if (estadoProyecto == null) {
                redirectAttributes.addFlashAttribute("error", "El estado proyecto: " + idEstadoProyecto + " no existe");
                return "redirect:/views/estadosProyecto/";
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "No se encontró el estado proyecto: " + idEstadoProyecto);
            return "redirect:/views/estadosProyecto/";
        }

        model.addAttribute("titulo", "Editar Estado Proyecto");
        model.addAttribute("estadoProyecto", estadoProyecto);

        return "/views/estadosProyecto/crearEstadoProyecto";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/eliminar/{idEstadoProyecto}")
    public String eliminarEstadoProyecto(@PathVariable("idEstadoProyecto") Long idEstadoProyecto, RedirectAttributes redirectAttributes) {
        EstadoProyecto estadoProyecto = null;

        // Validar que exista el estado proyecto
        if (idEstadoProyecto > 0) {
            estadoProyecto = new EstadoProyecto();
            estadoProyecto.setIdEstadoProyecto(idEstadoProyecto);
            estadoProyecto = estadoProyectoService.buscarEstadoProyecto(estadoProyecto);

            if (estadoProyecto == null) {
                redirectAttributes.addFlashAttribute("error", "El estado proyecto: " + idEstadoProyecto + " no existe");
                return "redirect:/views/estadosProyecto/";
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "No se encontró el estado proyecto: " + idEstadoProyecto);
            return "redirect:/views/estadosProyecto/";
        }

        estadoProyectoService.eliminarEstadoProyecto(estadoProyecto);

        redirectAttributes.addFlashAttribute("success", "Estado Proyecto: " + estadoProyecto.getIdEstadoProyecto() + " eliminado exitosamente");
        return "redirect:/views/estadosProyecto/";
    }
    
}
