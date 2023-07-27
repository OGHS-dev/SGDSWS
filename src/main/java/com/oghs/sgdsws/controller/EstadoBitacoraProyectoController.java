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

    private final String RUTA_VISTA = "/views/estadosBitacoraProyecto/";

    @Autowired
    private EstadoBitacoraProyectoService estadoBitacoraProyectoService;

    @Secured("ROLE_ADMIN")
    @GetMapping("/")
    public String verEstadosBitacoraProyecto(@RequestParam(value = "numeroPagina", required = false, defaultValue = "1") int numeroPagina, @RequestParam(value = "tamano", required = false, defaultValue = "5") int tamano, Model model) {
        model.addAttribute("titulo", "Estados de Evento");
        model.addAttribute("estadosBitacoraProyecto", estadoBitacoraProyectoService.obtenerEstadosBitacoraProyectoPaginado(numeroPagina, tamano));

        return RUTA_VISTA + "verEstadosBitacoraProyecto";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/crear")
    public String crearEstadoBitacoraProyecto(Model model) {
        model.addAttribute("titulo", "Nuevo Estado de Evento");
        model.addAttribute("estadoBitacoraProyecto", new EstadoBitacoraProyecto());

        return RUTA_VISTA + "crearEstadoBitacoraProyecto";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/guardar")
    public String guardarEstadoBitacoraProyecto(@Valid @ModelAttribute EstadoBitacoraProyecto estadoBitacoraProyecto, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        // Validar errores en el formulario
        if (bindingResult.hasErrors()) {
            model.addAttribute("titulo", "Crear/Editar Estado de Evento");
            model.addAttribute("estadoBitacoraProyecto", estadoBitacoraProyecto);

            System.err.println("Error en los datos proporcionados");

            return RUTA_VISTA + "crearEstadoBitacoraProyecto";
        }

        estadoBitacoraProyectoService.guardarEstadoBitacoraProyecto(estadoBitacoraProyecto);;

        redirectAttributes.addFlashAttribute("success", "Estado de Evento: " + estadoBitacoraProyecto.getCodigo() + " guardado exitosamente");

        return "redirect:" + RUTA_VISTA;
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

                return "redirect:" + RUTA_VISTA;
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "No se encontró el estado bitácora servicio: " + idEstadoBitacoraProyecto);
            
            return "redirect:" + RUTA_VISTA;
        }

        model.addAttribute("titulo", "Editar Estado de Evento");
        model.addAttribute("estadoBitacoraProyecto", estadoBitacoraProyecto);

        return RUTA_VISTA + "crearEstadoBitacoraProyecto";
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
                
                return "redirect:" + RUTA_VISTA;
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "No se encontró el estado bitácora servicio: " + idEstadoBitacoraProyecto);

            return "redirect:" + RUTA_VISTA;
        }

        estadoBitacoraProyectoService.eliminarEstadoBitacoraProyecto(estadoBitacoraProyecto);

        redirectAttributes.addFlashAttribute("success", "Estado de Evento: " + estadoBitacoraProyecto.getCodigo() + " eliminado exitosamente");

        return "redirect:" + RUTA_VISTA;
    }
    
}
