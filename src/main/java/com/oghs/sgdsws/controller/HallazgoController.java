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

import com.oghs.sgdsws.model.entity.Hallazgo;
import com.oghs.sgdsws.service.HallazgoService;

import jakarta.validation.Valid;

/**
 * HallazgoController es la clase controlador para ejecutar las
 * operaciones CRUD de hallazgos.
 * 
 * @author oghs
 * @version 1.0
 */
@Controller
@RequestMapping("/views/hallazgos")
public class HallazgoController {
    
    private final String RUTA_VISTA = "/views/hallazgos/";

    @Autowired
    private HallazgoService hallazgoService;

    @Secured("ROLE_ADMIN")
    @GetMapping("/")
    public String verHallazgos(@RequestParam(value = "numeroPagina", required = false, defaultValue = "1") int numeroPagina, @RequestParam(value = "tamano", required = false, defaultValue = "5") int tamano, Model model) {
        model.addAttribute("titulo", "Hallazgos");
        model.addAttribute("hallazgos", hallazgoService.obtenerHallazgosPaginado(numeroPagina, tamano));

        return RUTA_VISTA + "verHallazgos";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/crear")
    public String crearHallazgo(Model model) {
        model.addAttribute("titulo", "Nuevo Hallazgo");
        model.addAttribute("hallazgo", new Hallazgo());

        return RUTA_VISTA + "crearHallazgo";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/guardar")
    public String guardarHallazgo(@Valid @ModelAttribute Hallazgo hallazgo, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        // Validar errores en el formulario
        if (bindingResult.hasErrors()) {
            model.addAttribute("titulo", "Crear/Editar Hallazgo");
            model.addAttribute("hallazgo", new Hallazgo());

            String errores = "Error en los datos proporcionados:\n\n" + bindingResult.getFieldErrors().stream().map(error -> error.getDefaultMessage() + "\n").collect(Collectors.joining());
            model.addAttribute("warning", errores);

            return RUTA_VISTA + "crearHallazgo";
        }

        hallazgoService.guardarHallazgo(hallazgo);

        redirectAttributes.addFlashAttribute("success", String.format("Hallazgo: %s guardado exitosamente", hallazgo.getCodigo()));

        return "redirect:" + RUTA_VISTA;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/editar/{idHallazgo}")
    public String editarHallazgo(@PathVariable("idHallazgo") Long idHallazgo, Model model, RedirectAttributes redirectAttributes) {
        // Validar que exista el hallazgo
        Hallazgo hallazgo = this.validarHallazgo(idHallazgo);

        if (Objects.isNull(hallazgo)) {
            redirectAttributes.addFlashAttribute("error", String.format("El hallazgo: %d no existe", idHallazgo));
            
            return "redirect:" + RUTA_VISTA;
        }

        model.addAttribute("titulo", "Editar Hallazgo");
        model.addAttribute("hallazgo", hallazgo);
        
        return RUTA_VISTA + "crearHallazgo";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/eliminar/{idHallazgo}")
    public String eliminarHallazgo(@PathVariable("idHallazgo") Long idHallazgo, RedirectAttributes redirectAttributes) {
        // Validar que exista el hallazgo
        Hallazgo hallazgo = this.validarHallazgo(idHallazgo);

        if (Objects.isNull(hallazgo)) {
            redirectAttributes.addFlashAttribute("error", String.format("El hallazgo: %d no existe", idHallazgo));
        } else {
            hallazgoService.eliminarHallazgo(hallazgo);

            redirectAttributes.addFlashAttribute("success", String.format("Hallazgo: %s eliminado exitosamente", hallazgo.getCodigo()));
        }

        return "redirect:" + RUTA_VISTA;
    }

    /**
     * Retorna un objeto Hallazgo si éste existe y se encontró.
     *
     * @param idUsuario el id del hallazgo a buscar
     * @return hallazgo el objeto del hallazgo encontrado
     */
    private Hallazgo validarHallazgo(Long idHallazgo) {
        Hallazgo hallazgo = null;

        if (idHallazgo > 0) {
            hallazgo = new Hallazgo();
            hallazgo.setIdHallazgo(idHallazgo);
            hallazgo = hallazgoService.buscarHallazgo(hallazgo);

            // No se encontró el hallazgo
            if (Objects.isNull(hallazgo)) {
                return null;
            }
        }

        return hallazgo;
    }
}
