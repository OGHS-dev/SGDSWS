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

import com.oghs.sgdsws.model.entity.EstadoBitacoraProyecto;
import com.oghs.sgdsws.service.EstadoBitacoraProyectoService;

import jakarta.validation.Valid;

/**
 * EstadoBitacoraProyectoController es la clase controlador para ejecutar las
 * operaciones CRUD de estados de bitácora de proyecto.
 * 
 * @author oghs
 * @version 1.0
 */
@Controller
@RequestMapping("/views/estadosBitacoraProyecto")
public class EstadoBitacoraProyectoController {

    private static final String RUTA_VISTA = "/views/estadosBitacoraProyecto/";

    private final EstadoBitacoraProyectoService estadoBitacoraProyectoService;

    public EstadoBitacoraProyectoController(EstadoBitacoraProyectoService estadoBitacoraProyectoService) {
        this.estadoBitacoraProyectoService = estadoBitacoraProyectoService;
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPERVISOR", "ROLE_AUDITOR", "ROLE_REVISOR"})
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

            String errores = "Error en los datos proporcionados:\n\n" + bindingResult.getFieldErrors().stream().map(error -> error.getDefaultMessage() + "\n").collect(Collectors.joining());
            model.addAttribute("warning", errores);

            return RUTA_VISTA + "crearEstadoBitacoraProyecto";
        }

        estadoBitacoraProyectoService.guardarEstadoBitacoraProyecto(estadoBitacoraProyecto);;

        redirectAttributes.addFlashAttribute("success", "Estado de Evento: " + estadoBitacoraProyecto.getCodigo() + " guardado exitosamente");

        return "redirect:" + RUTA_VISTA;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/editar/{idEstadoBitacoraProyecto}")
    public String editarEstadoBitacoraProyecto(@PathVariable("idEstadoBitacoraProyecto") Long idEstadoBitacoraProyecto, Model model, RedirectAttributes redirectAttributes) {
        // Validar que exista el estado bitácora proyecto
        EstadoBitacoraProyecto estadoBitacoraProyecto = this.validarEstadoBitacoraProyecto(idEstadoBitacoraProyecto);

        if (Objects.isNull(estadoBitacoraProyecto)) {
            redirectAttributes.addFlashAttribute("error", String.format("El estado de evento: %d no existe", idEstadoBitacoraProyecto));

            return "redirect:" + RUTA_VISTA;
        }
        
        model.addAttribute("titulo", "Editar Estado de Evento");
        model.addAttribute("estadoBitacoraProyecto", estadoBitacoraProyecto);

        return RUTA_VISTA + "crearEstadoBitacoraProyecto";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/eliminar/{idEstadoBitacoraProyecto}")
    public String eliminarEstadoBitacoraProyecto(@PathVariable("idEstadoBitacoraProyecto") Long idEstadoBitacoraProyecto, RedirectAttributes redirectAttributes) {
        // Validar que exista el estado bitácora proyecto
        EstadoBitacoraProyecto estadoBitacoraProyecto = this.validarEstadoBitacoraProyecto(idEstadoBitacoraProyecto);

        if (Objects.isNull(estadoBitacoraProyecto)) {
            redirectAttributes.addFlashAttribute("error", String.format("El estado de evento: %d no existe", idEstadoBitacoraProyecto));
        } else {
            if (estadoBitacoraProyecto.getBitacoraProyecto().isEmpty()) {
                estadoBitacoraProyectoService.eliminarEstadoBitacoraProyecto(estadoBitacoraProyecto);

                redirectAttributes.addFlashAttribute("success", String.format("Estado de evento: %s eliminado exitosamente", estadoBitacoraProyecto.getCodigo()));
            } else {
                String bitacoraProyecto = estadoBitacoraProyecto.getBitacoraProyecto().stream().map(bp -> bp.getDescripcion() + ", ").collect(Collectors.joining());
                redirectAttributes.addFlashAttribute("warning", String.format("El estado de evento: %s no se puede eliminar ya que se encuentra asociado a los siguientes eventos: %s", estadoBitacoraProyecto.getCodigo(), bitacoraProyecto));
            }
        }

        return "redirect:" + RUTA_VISTA;
    }

    /**
     * Retorna un objeto EstadoBitacoraProyecto si éste existe y se encontró.
     *
     * @param idEstadoBitacoraProyecto el id del estado de bitácora de proyecto a buscar
     * @return estadoBitacoraProyecto el objeto del estado de bitácora de proyecto encontrado
     */
    private EstadoBitacoraProyecto validarEstadoBitacoraProyecto(Long idEstadoBitacoraProyecto) {
        EstadoBitacoraProyecto estadoBitacoraProyecto = null;

        if (idEstadoBitacoraProyecto > 0) {
            estadoBitacoraProyecto = new EstadoBitacoraProyecto();
            estadoBitacoraProyecto.setIdEstadoBitacoraProyecto(idEstadoBitacoraProyecto);
            estadoBitacoraProyecto = estadoBitacoraProyectoService.buscarEstadoBitacoraProyecto(estadoBitacoraProyecto);

            // No se encontró el estado de bitácora de proyecto
            if (Objects.isNull(estadoBitacoraProyecto)) {
                return null;
            }
        }

        return estadoBitacoraProyecto;
    }
    
}
