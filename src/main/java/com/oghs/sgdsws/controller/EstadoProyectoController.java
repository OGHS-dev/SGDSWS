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

import com.oghs.sgdsws.model.entity.EstadoProyecto;
import com.oghs.sgdsws.service.EstadoProyectoService;

import jakarta.validation.Valid;

/**
 * EstadoProyectoController es la clase controlador para ejecutar las
 * operaciones CRUD de estados de proyecto.
 * 
 * @author oghs
 * @version 1.0
 */
@Controller
@RequestMapping("/views/estadosProyecto")
public class EstadoProyectoController {

    private static final String RUTA_VISTA = "/views/estadosProyecto/";
    
    private final EstadoProyectoService estadoProyectoService;

    public EstadoProyectoController(EstadoProyectoService estadoProyectoService) {
        this.estadoProyectoService = estadoProyectoService;
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPERVISOR", "ROLE_AUDITOR", "ROLE_REVISOR"})
    @GetMapping("/")
    public String verEstadosProyecto(@RequestParam(value = "numeroPagina", required = false, defaultValue = "1") int numeroPagina, @RequestParam(value = "tamano", required = false, defaultValue = "5") int tamano, Model model) {
        model.addAttribute("titulo", "Estados Proyecto");
        model.addAttribute("estadosProyecto", estadoProyectoService.obtenerEstadosProyectoPaginado(numeroPagina, tamano));

        return RUTA_VISTA + "verEstadosProyecto";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/crear")
    public String crearEstadoProyecto(Model model) {
        model.addAttribute("titulo", "Nuevo Estado Proyecto");
        model.addAttribute("estadoProyecto", new EstadoProyecto());

        return RUTA_VISTA + "crearEstadoProyecto";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/guardar")
    public String guardarEstadoProyecto(@Valid @ModelAttribute EstadoProyecto estadoProyecto, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        // Validar errores en el formulario
        if (bindingResult.hasErrors()) {
            model.addAttribute("titulo", "Crear/Editar Estado Proyecto");
            model.addAttribute("estadoProyecto", estadoProyecto);

            String errores = "Error en los datos proporcionados:\n\n" + bindingResult.getFieldErrors().stream().map(error -> error.getDefaultMessage() + "\n").collect(Collectors.joining());
            model.addAttribute("warning", errores);

            return RUTA_VISTA + "crearEstadoProyecto";
        }

        estadoProyectoService.guardarEstadoProyecto(estadoProyecto);

        redirectAttributes.addFlashAttribute("success", "Estado Proyecto: " + estadoProyecto.getCodigo() + " guardado exitosamente");
        
        return "redirect:" + RUTA_VISTA;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/editar/{idEstadoProyecto}")
    public String editarEstadoProyecto(@PathVariable("idEstadoProyecto") Long idEstadoProyecto, Model model, RedirectAttributes redirectAttributes) {
        // Validar que exista el estado de proyecto
        EstadoProyecto estadoProyecto = this.validarEstadoProyecto(idEstadoProyecto);

        if (Objects.isNull(estadoProyecto)) {
            redirectAttributes.addFlashAttribute("error", String.format("El estado de proyecto: %d no existe", idEstadoProyecto));
            
            return "redirect:" + RUTA_VISTA;
        }

        model.addAttribute("titulo", "Editar Estado Proyecto");
        model.addAttribute("estadoProyecto", estadoProyecto);

        return RUTA_VISTA + "crearEstadoProyecto";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/eliminar/{idEstadoProyecto}")
    public String eliminarEstadoProyecto(@PathVariable("idEstadoProyecto") Long idEstadoProyecto, RedirectAttributes redirectAttributes) {
        // Validar que exista el estado de proyecto
        EstadoProyecto estadoProyecto = this.validarEstadoProyecto(idEstadoProyecto);

        if (Objects.isNull(estadoProyecto)) {
            redirectAttributes.addFlashAttribute("error", String.format("El estado de proyecto: %d no existe", idEstadoProyecto));
        } else {
            if (estadoProyecto.getProyecto().isEmpty()) {
                estadoProyectoService.eliminarEstadoProyecto(estadoProyecto);

                redirectAttributes.addFlashAttribute("success", "Estado Proyecto: " + estadoProyecto.getCodigo() + " eliminado exitosamente");
            } else {
                String proyectos = estadoProyecto.getProyecto().stream().map(p -> p.getNombre() + ", ").collect(Collectors.joining());
                redirectAttributes.addFlashAttribute("warning", String.format("El estado de proyecto: %s no se puede eliminar ya que se encuentra asociado a los siguientes proyectos: %s", estadoProyecto.getCodigo(), proyectos));
            }
        }
        
        return "redirect:" + RUTA_VISTA;
    }
    
    /**
     * Retorna un objeto EstadoProyecto si éste existe y se encontró.
     *
     * @param idUsuario el id del estado de proyecto a buscar
     * @return estadoProyecto el objeto del estado de proyecto encontrado
     */
    private EstadoProyecto validarEstadoProyecto(Long idEstadoProyecto) {
        EstadoProyecto estadoProyecto = null;

        if (idEstadoProyecto > 0) {
            estadoProyecto = new EstadoProyecto();
            estadoProyecto.setIdEstadoProyecto(idEstadoProyecto);
            estadoProyecto = estadoProyectoService.buscarEstadoProyecto(estadoProyecto);

            // No se encontró el estado de proyecto
            if (Objects.isNull(estadoProyecto)) {
                return null;
            }
        }

        return estadoProyecto;
    }
}
