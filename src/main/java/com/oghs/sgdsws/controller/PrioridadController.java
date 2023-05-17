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

import com.oghs.sgdsws.model.entity.Prioridad;
import com.oghs.sgdsws.model.service.PrioridadService;

import jakarta.validation.Valid;

/**
 *
 * @author oghs
 */
@Controller
@RequestMapping("/views/prioridades")
public class PrioridadController {
    
    @Autowired
    private PrioridadService prioridadService;

    @Secured("ROLE_ADMIN")
    @GetMapping("/")
    public String verPrioridades(@RequestParam(value = "numeroPagina", required = false, defaultValue = "1") int numeroPagina, @RequestParam(value = "tamano", required = false, defaultValue = "5") int tamano, Model model) {
        model.addAttribute("titulo", "Prioridades");
        model.addAttribute("prioridades", prioridadService.obtenerPrioridadesPaginado(numeroPagina, tamano));

        return "/views/prioridades/verPrioridades";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/crear")
    public String crearPrioridad(Model model) {
        model.addAttribute("titulo", "Nueva Prioridad");
        model.addAttribute("prioridad", new Prioridad());

        return "/views/prioridades/crearPrioridad";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/guardar")
    public String guardarPrioridad(@Valid @ModelAttribute Prioridad prioridad, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        // Validar errores en el formulario
        if (bindingResult.hasErrors()) {
            model.addAttribute("titulo", "Editar Prioridad");
            model.addAttribute("prioridad", prioridad);

            System.err.println("Error en los datos proporcionados");
            return "/views/prioridades/crearPrioridad";
        }

        prioridadService.guardarPrioridad(prioridad);

        redirectAttributes.addFlashAttribute("success", "Prioridad: " + prioridad.getIdPrioridad() + " guardada exitosamente");
        return "redirect:/views/prioridades/";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/editar/{idPrioridad}")
    public String editarPrioridad(@PathVariable("idPrioridad") Long idPrioridad, Model model, RedirectAttributes redirectAttributes) {
        Prioridad prioridad = null;

        // Validar que exista la prioridad
        if (idPrioridad > 0) {
            prioridad = new Prioridad();
            prioridad.setIdPrioridad(idPrioridad);
            prioridad = prioridadService.buscarPrioridad(prioridad);

            if (prioridad == null) {
                redirectAttributes.addFlashAttribute("error", "La prioridad: " + idPrioridad + " no existe");
                return "redirect:/views/prioridades/";
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "No se encontró la prioridad: " + idPrioridad);
            return "redirect:/views/prioridades/";
        }

        model.addAttribute("titulo", "Editar Prioridad");
        model.addAttribute("prioridad", prioridad);

        return "/views/prioridades/crearPrioridad";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/eliminar/{idPrioridad}")
    public String eliminarPrioridad(@PathVariable("idPrioridad") Long idPrioridad, RedirectAttributes redirectAttributes) {
        Prioridad prioridad = null;

        // Validar que exista la prioridad
        if (idPrioridad > 0) {
            prioridad = new Prioridad();
            prioridad.setIdPrioridad(idPrioridad);
            prioridad = prioridadService.buscarPrioridad(prioridad);

            if (prioridad == null) {
                redirectAttributes.addFlashAttribute("error", "La prioridad: " + idPrioridad + " no existe");
                return "redirect:/views/prioridades/";
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "No se encontró la prioridad: " + idPrioridad);
            return "redirect:/views/prioridades/";
        }

        prioridadService.eliminarPrioridad(prioridad);

        redirectAttributes.addFlashAttribute("success", "Prioridad: " + prioridad.getIdPrioridad() + " eliminada exitosamente");
        return "redirect:/views/prioridades/";
    }
}
