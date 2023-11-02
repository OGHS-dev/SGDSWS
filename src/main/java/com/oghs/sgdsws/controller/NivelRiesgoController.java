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

import com.oghs.sgdsws.model.entity.NivelRiesgo;
import com.oghs.sgdsws.service.NivelRiesgoService;

import jakarta.validation.Valid;

/**
 * NivelRiesgoController es la clase controlador para ejecutar las
 * operaciones CRUD de niveles de riesgo.
 * 
 * @author oghs
 * @version 1.0
 */
@Controller
@RequestMapping("/views/nivelesRiesgo")
public class NivelRiesgoController {

    private static final String RUTA_VISTA = "/views/nivelesRiesgo/";

    private final NivelRiesgoService nivelRiesgoService;

    public NivelRiesgoController(NivelRiesgoService nivelRiesgoService) {
        this.nivelRiesgoService = nivelRiesgoService;
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPERVISOR", "ROLE_AUDITOR", "ROLE_REVISOR"})
    @GetMapping("/")
    public String verNivelesRiesgo(@RequestParam(value = "numeroPagina", required = false, defaultValue = "1") int numeroPagina, @RequestParam(value = "tamano", required = false, defaultValue = "5") int tamano, Model model) {
        model.addAttribute("titulo", "Niveles de Riesgo");
        model.addAttribute("nivelesRiesgo", nivelRiesgoService.obtenerNivelesRiesgoPaginado(numeroPagina, tamano));

        return RUTA_VISTA + "verNivelesRiesgo";
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPERVISOR", "ROLE_AUDITOR"})
    @GetMapping("/crear")
    public String crearNivelRiesgo(Model model) {
        model.addAttribute("titulo", "Nuevo Nivel de Riesgo");
        model.addAttribute("nivelRiesgo", new NivelRiesgo());

        return RUTA_VISTA + "crearNivelRiesgo";
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPERVISOR", "ROLE_AUDITOR"})
    @PostMapping("/guardar")
    public String guardarNivelRiesgo(@Valid @ModelAttribute NivelRiesgo nivelRiesgo, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        // Validar errores en el formulario
        if (bindingResult.hasErrors()) {
            model.addAttribute("titulo", "Crear/Editar Nivel de Riesgo");
            model.addAttribute("nivelRiesgo", nivelRiesgo);

            String errores = "Error en los datos proporcionados:\n\n" + bindingResult.getFieldErrors().stream().map(error -> error.getDefaultMessage() + "\n").collect(Collectors.joining());
            model.addAttribute("warning", errores);

            return RUTA_VISTA + "crearNivelRiesgo";
        }

        nivelRiesgoService.guardarNivelRiesgo(nivelRiesgo);

        redirectAttributes.addFlashAttribute("success", String.format("Nivel de riesgo: %s guardado exitosamente", nivelRiesgo.getCodigo()));

        return "redirect:" + RUTA_VISTA;
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPERVISOR"})
    @GetMapping("/editar/{idNivelRiesgo}")
    public String editarImpacto(@PathVariable("idNivelRiesgo") Long idNivelRiesgo, Model model, RedirectAttributes redirectAttributes) {
        // Validar que exista el nivel de riesgo
        NivelRiesgo nivelRiesgo = this.validarNivelRiesgo(idNivelRiesgo);

        if (Objects.isNull(nivelRiesgo)) {
            redirectAttributes.addFlashAttribute("error", String.format("El nivel de riesgo: %d no existe", idNivelRiesgo));
            
            return "redirect:" + RUTA_VISTA;
        }

        model.addAttribute("titulo", "Editar Nivel de Riesgo");
        model.addAttribute("nivelRiesgo", nivelRiesgo);

        return RUTA_VISTA + "crearNivelRiesgo";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/eliminar/{idNivelRiesgo}")
    public String eliminarImpacto(@PathVariable("idNivelRiesgo") Long idNivelRiesgo, RedirectAttributes redirectAttributes) {
        // Validar que exista el nivel de riesgo
        NivelRiesgo nivelRiesgo = this.validarNivelRiesgo(idNivelRiesgo);

        if (Objects.isNull(nivelRiesgo)) {
            redirectAttributes.addFlashAttribute("error", String.format("El nivel de riesgo: %d no existe", idNivelRiesgo));
        } else {
            if (nivelRiesgo.getBitacoraProyecto().isEmpty()) {
                nivelRiesgoService.eliminarNivelRiesgo(nivelRiesgo);

                redirectAttributes.addFlashAttribute("success", String.format("Nivel de riesgo: %s eliminado exitosamente", nivelRiesgo.getCodigo()));
            } else {
                String bitacoraProyecto = nivelRiesgo.getBitacoraProyecto().stream().map(bp -> bp.getDescripcion() + ", ").collect(Collectors.joining());
                redirectAttributes.addFlashAttribute("warning", String.format("El nivel de riesgo: %s no se puede eliminar ya que se encuentra asociado a los siguientes eventos: %s", nivelRiesgo.getCodigo(), bitacoraProyecto));
            }
        }
        
        return "redirect:" + RUTA_VISTA;
    }

    /**
     * Retorna un objeto NivelRiesgo si éste existe y se encontró.
     *
     * @param idNivelRiesgo el id del nivel de riesgo a buscar
     * @return nivel de riesgo el objeto del nivel de riesgo encontrado
     */
    private NivelRiesgo validarNivelRiesgo(Long idNivelRiesgo) {
        NivelRiesgo nivelRiesgo = null;

        if (idNivelRiesgo > 0) {
            nivelRiesgo = new NivelRiesgo();
            nivelRiesgo.setIdNivelRiesgo(idNivelRiesgo);
            nivelRiesgo = nivelRiesgoService.buscarNivelRiesgo(nivelRiesgo);

            // No se encontró el nivel de riesgo
            if (Objects.isNull(nivelRiesgo)) {
                return null;
            }
        }

        return nivelRiesgo;
    }
    
}
