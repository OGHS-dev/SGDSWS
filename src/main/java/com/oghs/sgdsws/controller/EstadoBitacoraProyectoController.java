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

import com.oghs.sgdsws.model.entity.EstadoBitacoraProyecto;
import com.oghs.sgdsws.model.service.EstadoBitacoraProyectoService;

import jakarta.validation.Valid;

/**
 *
 * @author oghs
 */
@Controller
@RequestMapping("/views/estadosBitacoraProyecto")
public class EstadoBitacoraProyectoController {

    @Autowired
    private EstadoBitacoraProyectoService estadoBitacoraProyectoService;

    @Secured("ROLE_ADMIN")
    @GetMapping("/")
    public String verEstadosBitacoraProyecto(@RequestParam(value = "numeroPagina", required = false, defaultValue = "1") int numeroPagina, @RequestParam(value = "tamano", required = false, defaultValue = "5") int tamano, Model model) {
        model.addAttribute("titulo", "Estados Bitácora Proyecto");
        model.addAttribute("estadosBitacoraProyecto", estadoBitacoraProyectoService.obtenerEstadosBitacoraProyectoPaginado(numeroPagina, tamano));

        return "/views/estadosBitacoraProyecto/verEstadosBitacoraProyecto";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/crear")
    public String crearEstadoBitacoraProyecto(Model model) {
        model.addAttribute("titulo", "Nuevo Estado Bitácora Proyecto");
        model.addAttribute("estadoBitacoraProyecto", new EstadoBitacoraProyecto());

        return "/views/estadosBitacoraProyecto/crearEstadoBitacoraProyecto";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/guardar")
    public String guardarEstadoBitacoraProyecto(@Valid @ModelAttribute EstadoBitacoraProyecto estadoBitacoraProyecto, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        // Validar errores en el formulario
        if (bindingResult.hasErrors()) {
            model.addAttribute("titulo", "Editar Estado Bitácora Proyecto");
            model.addAttribute("estadoBitacoraProyecto", estadoBitacoraProyecto);

            System.err.println("Error en los datos proporcionados");
            return "/views/estadosBitacoraProyecto/crearEstadoBitacoraProyecto";
        }

        estadoBitacoraProyectoService.guardarEstadoBitacoraProyecto(estadoBitacoraProyecto);;

        redirectAttributes.addFlashAttribute("success", "Estado Bitácora Proyecto: " + estadoBitacoraProyecto.getIdEstadoBitacoraProyecto() + " guardado exitosamente");
        return "redirect:/views/estadosBitacoraProyecto/";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/editar/{idEstadoBitacoraProyecto}")
    public String editarEstadoBitacoraProyecto(@PathVariable("idEstadoBitacoraProyecto") Long idEstadoBitacoraProyecto, Model model, RedirectAttributes redirectAttributes) {
        EstadoBitacoraProyecto estadoBitacoraProyecto = null;

        // Validar que exista el estado bitacora proyecto
        if (idEstadoBitacoraProyecto > 0) {
            estadoBitacoraProyecto = new EstadoBitacoraProyecto();
            estadoBitacoraProyecto.setIdEstadoBitacoraProyecto(idEstadoBitacoraProyecto);
            estadoBitacoraProyecto = estadoBitacoraProyectoService.buscarEstadoBitacoraProyecto(estadoBitacoraProyecto);

            if (estadoBitacoraProyecto == null) {
                redirectAttributes.addFlashAttribute("error", "El estado bitácora servicio: " + idEstadoBitacoraProyecto + " no existe");
                return "redirect:/views/estadosBitacoraProyecto/";
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "No se encontró el estado bitácora servicio: " + idEstadoBitacoraProyecto);
            return "redirect:/views/estadosBitacoraProyecto/";
        }

        model.addAttribute("titulo", "Editar Estado Bitácora Proyecto");
        model.addAttribute("estadoBitacoraProyecto", estadoBitacoraProyecto);

        return "/views/estadosBitacoraProyecto/crearEstadoBitacoraProyecto";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/eliminar/{idEstadoBitacoraProyecto}")
    public String eliminarEstadoBitacoraProyecto(@PathVariable("idEstadoBitacoraProyecto") Long idEstadoBitacoraProyecto, RedirectAttributes redirectAttributes) {
        EstadoBitacoraProyecto estadoBitacoraProyecto = null;

        // Validar que exista el estado bitacora proyecto
        if (idEstadoBitacoraProyecto > 0) {
            estadoBitacoraProyecto = new EstadoBitacoraProyecto();
            estadoBitacoraProyecto.setIdEstadoBitacoraProyecto(idEstadoBitacoraProyecto);
            estadoBitacoraProyecto = estadoBitacoraProyectoService.buscarEstadoBitacoraProyecto(estadoBitacoraProyecto);

            if (estadoBitacoraProyecto == null) {
                redirectAttributes.addFlashAttribute("error", "El estado bitácora servicio: " + idEstadoBitacoraProyecto + " no existe");
                return "redirect:/views/estadosBitacoraProyecto/";
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "No se encontró el estado bitácora servicio: " + idEstadoBitacoraProyecto);
            return "redirect:/views/estadosBitacoraProyecto/";
        }

        estadoBitacoraProyectoService.eliminarEstadoBitacoraProyecto(estadoBitacoraProyecto);

        redirectAttributes.addFlashAttribute("success", "Estado Bitácora Proyecto: " + estadoBitacoraProyecto.getIdEstadoBitacoraProyecto() + " eliminado exitosamente");
        return "redirect:/views/estadosBitacoraProyecto/";
    }
    
}
