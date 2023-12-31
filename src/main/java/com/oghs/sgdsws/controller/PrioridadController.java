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

import com.oghs.sgdsws.model.entity.Prioridad;
import com.oghs.sgdsws.service.PrioridadService;

import jakarta.validation.Valid;

/**
 * PrioridadController es la clase controlador para ejecutar las
 * operaciones CRUD de prioridades.
 * 
 * @author oghs
 * @version 1.0
 */
@Controller
@RequestMapping("/views/prioridades")
public class PrioridadController {

    private static final String RUTA_VISTA = "/views/prioridades/";
    
    private final PrioridadService prioridadService;

    public PrioridadController(PrioridadService prioridadService) {
        this.prioridadService = prioridadService;
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPERVISOR", "ROLE_AUDITOR", "ROLE_REVISOR"})
    @GetMapping("/")
    public String verPrioridades(@RequestParam(value = "numeroPagina", required = false, defaultValue = "1") int numeroPagina, @RequestParam(value = "tamano", required = false, defaultValue = "5") int tamano, Model model) {
        model.addAttribute("titulo", "Prioridades");
        model.addAttribute("prioridades", prioridadService.obtenerPrioridadesPaginado(numeroPagina, tamano));

        return RUTA_VISTA + "verPrioridades";
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPERVISOR", "ROLE_AUDITOR"})
    @GetMapping("/crear")
    public String crearPrioridad(Model model) {
        model.addAttribute("titulo", "Nueva Prioridad");
        model.addAttribute("prioridad", new Prioridad());

        return RUTA_VISTA + "crearPrioridad";
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPERVISOR", "ROLE_AUDITOR"})
    @PostMapping("/guardar")
    public String guardarPrioridad(@Valid @ModelAttribute Prioridad prioridad, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        // Validar errores en el formulario
        if (bindingResult.hasErrors()) {
            model.addAttribute("titulo", "Crear/Editar Prioridad");
            model.addAttribute("prioridad", prioridad);

            String errores = "Error en los datos proporcionados:\n\n" + bindingResult.getFieldErrors().stream().map(error -> error.getDefaultMessage() + "\n").collect(Collectors.joining());
            model.addAttribute("warning", errores);
            
            return RUTA_VISTA + "crearPrioridad";
        }

        prioridadService.guardarPrioridad(prioridad);

        redirectAttributes.addFlashAttribute("success", String.format("Prioridad: %s guardado exitosamente", prioridad.getCodigo()));
        
        return "redirect:" + RUTA_VISTA;
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPERVISOR"})
    @GetMapping("/editar/{idPrioridad}")
    public String editarPrioridad(@PathVariable("idPrioridad") Long idPrioridad, Model model, RedirectAttributes redirectAttributes) {
        // Validar que exista la prioridad
        Prioridad prioridad = this.validarPrioridad(idPrioridad);

        if (Objects.isNull(prioridad)) {
            redirectAttributes.addFlashAttribute("error", String.format("La prioridad: %d no existe", idPrioridad));
            
            return "redirect:" + RUTA_VISTA;
        }

        model.addAttribute("titulo", "Editar Prioridad");
        model.addAttribute("prioridad", prioridad);

        return RUTA_VISTA + "crearPrioridad";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/eliminar/{idPrioridad}")
    public String eliminarPrioridad(@PathVariable("idPrioridad") Long idPrioridad, RedirectAttributes redirectAttributes) {
        // Validar que exista la prioridad
        Prioridad prioridad = this.validarPrioridad(idPrioridad);
        
        if (Objects.isNull(prioridad)) {
            redirectAttributes.addFlashAttribute("error", String.format("La prioridad: %d no existe", idPrioridad));
            
            return "redirect:" + RUTA_VISTA;
        } else {
            if (prioridad.getBitacoraProyecto().isEmpty()) {
                prioridadService.eliminarPrioridad(prioridad);

                redirectAttributes.addFlashAttribute("success", String.format("Prioridad: %s eliminada exitosamente", prioridad.getCodigo()));
            } else {
                String bitacoraProyecto = prioridad.getBitacoraProyecto().stream().map(bp -> bp.getDescripcion() + ", ").collect(Collectors.joining());
                redirectAttributes.addFlashAttribute("warning", String.format("La prioridad: %s no se puede eliminar ya que se encuentra asociada a los siguientes eventos: %s", prioridad.getCodigo(), bitacoraProyecto));
            }
        }

        return "redirect:" + RUTA_VISTA;
    }

    /**
     * Retorna un objeto Prioridad si éste existe y se encontró.
     *
     * @param idPrioridad el id de la prioridad a buscar
     * @return prioridad el objeto de la prioridad encontrado
     */
    private Prioridad validarPrioridad(Long idPrioridad) {
        Prioridad prioridad = null;

        if (idPrioridad > 0) {
            prioridad = new Prioridad();
            prioridad.setIdPrioridad(idPrioridad);
            prioridad = prioridadService.buscarPrioridad(prioridad);

            // No se encontró la prioridad
            if (Objects.isNull(prioridad)) {
                return null;
            }
        }

        return prioridad;
    }

}
