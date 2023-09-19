package com.oghs.sgdsws.controller;

import java.util.Objects;
import java.util.stream.Collectors;

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

import com.oghs.sgdsws.model.entity.Incidente;
import com.oghs.sgdsws.service.IncidenteService;

import jakarta.validation.Valid;

/**
 * IncidenteController es la clase controlador para ejecutar las
 * operaciones CRUD de incidentes.
 * 
 * @author oghs
 * @version 1.0
 */
@Controller
@RequestMapping("/views/incidentes")
public class IncidenteController {
    
    private final String RUTA_VISTA = "/views/incidentes/";

    @Autowired
    private IncidenteService incidenteService;

    @Secured("ROLE_ADMIN")
    @GetMapping("/")
    public String verIncidentes(@RequestParam(value = "numeroPagina", required = false, defaultValue = "1") int numeroPagina, @RequestParam(value = "tamano", required = false, defaultValue = "5") int tamano, Model model) {
        model.addAttribute("titulo", "Incidentes");
        model.addAttribute("incidentes", incidenteService.obtenerIncidentesPaginado(numeroPagina, tamano));

        return RUTA_VISTA + "verIncidentes";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/crear")
    public String crearIncidente(Model model) {
        model.addAttribute("titulo", "Nuevo Incidente");
        model.addAttribute("incidente", new Incidente());

        return RUTA_VISTA + "crearIncidente";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/guardar")
    public String guardarIncidente(@Valid @ModelAttribute Incidente incidente, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        // Validar errores en el formulario
        if (bindingResult.hasErrors()) {
            model.addAttribute("titulo", "Crear/Editar Incidente");
            model.addAttribute("incidente", new Incidente());

            String errores = "Error en los datos proporcionados:\n\n" + bindingResult.getFieldErrors().stream().map(error -> error.getDefaultMessage() + "\n").collect(Collectors.joining());
            model.addAttribute("warning", errores);
            
            return RUTA_VISTA + "crearIncidente";
        }

        incidenteService.guardarIncidente(incidente);

        redirectAttributes.addFlashAttribute("success", String.format("Incidente: %s guardado exitosamente", incidente.getCodigo()));

        return "redirect:" + RUTA_VISTA;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/editar/{idIncidente}")
    public String editarIncidente(@PathVariable("idIncidente") Long idIncidente, Model model, RedirectAttributes redirectAttributes) {
        // Validar que exista el incidente
        Incidente incidente = this.validarIncidente(idIncidente);

        if (Objects.isNull(incidente)) {
            redirectAttributes.addFlashAttribute("error", String.format("El incidente: %d no existe", idIncidente));
            
            return "redirect:" + RUTA_VISTA;
        }

        model.addAttribute("titulo", "Editar Incidente");
        model.addAttribute("incidente", incidente);

        return RUTA_VISTA + "crearIncidente";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/eliminar/{idIncidente}")
    public String eliminarIncidente(@PathVariable("idIncidente") Long idIncidente, RedirectAttributes redirectAttributes) {
        // Validar que exista el incidente
        Incidente incidente = this.validarIncidente(idIncidente);

        if (Objects.isNull(incidente)) {
            redirectAttributes.addFlashAttribute("error", String.format("El incidente: %d no existe", idIncidente));
        } else {
            incidenteService.eliminarIncidente(incidente);
            
            redirectAttributes.addFlashAttribute("success", String.format("Incidente: %s eliminado exitosamente", incidente.getCodigo()));
        }

        return "redirect:" + RUTA_VISTA;
    }

    /**
     * Retorna un objeto Incidente si éste existe y se encontró.
     *
     * @param idUsuario el id del incidente a buscar
     * @return incidente el objeto del incidente encontrado
     */
    private Incidente validarIncidente(Long idIncidente) {
        Incidente incidente = null;

        if (idIncidente > 0) {
            incidente = new Incidente();
            incidente.setIdIncidente(idIncidente);
            incidente = incidenteService.buscarIncidente(incidente);

            // No se encontró el incidente
            if (Objects.isNull(incidente)) {
                return null;
            }
        }

        return incidente;
    }
}
