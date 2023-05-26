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

import com.oghs.sgdsws.model.entity.NivelRiesgo;
import com.oghs.sgdsws.model.service.NivelRiesgoService;

import jakarta.validation.Valid;

/**
 *
 * @author oghs
 */
@Controller
@RequestMapping("/views/nivelesRiesgo")
public class NivelRiesgoController {

    private final String RUTA_VISTA = "/views/nivelesRiesgo/";

    @Autowired
    private NivelRiesgoService nivelRiesgoService;

    @Secured("ROLE_ADMIN")
    @GetMapping("/")
    public String verNivelesRiesgo(@RequestParam(value = "numeroPagina", required = false, defaultValue = "1") int numeroPagina, @RequestParam(value = "tamano", required = false, defaultValue = "5") int tamano, Model model) {
        model.addAttribute("titulo", "Niveles de Riesgo");
        model.addAttribute("nivelesRiesgo", nivelRiesgoService.obtenerNivelesRiesgoPaginado(numeroPagina, tamano));

        return RUTA_VISTA + "verNivelesRiesgo";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/crear")
    public String crearNivelRiesgo(Model model) {
        model.addAttribute("titulo", "Nuevo Nivel de Riesgo");
        model.addAttribute("nivelRiesgo", new NivelRiesgo());

        return RUTA_VISTA + "crearNivelRiesgo";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/guardar")
    public String guardarNivelRiesgo(@Valid @ModelAttribute NivelRiesgo nivelRiesgo, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        // Validar errores en el formulario
        if (bindingResult.hasErrors()) {
            model.addAttribute("titulo", "Crear/Editar Nivel de Riesgo");
            model.addAttribute("nivelRiesgo", nivelRiesgo);

            System.err.println("Error en los datos proporcionados");

            return RUTA_VISTA + "crearNivelRiesgo";
        }

        nivelRiesgoService.guardarNivelRiesgo(nivelRiesgo);

        redirectAttributes.addFlashAttribute("success", "Nivel de Riesgo: " + nivelRiesgo.getCodigo() + " guardado exitosamente");

        return "redirect:" + RUTA_VISTA;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/editar/{idNivelRiesgo}")
    public String editarImpacto(@PathVariable("idNivelRiesgo") Long idNivelRiesgo, Model model, RedirectAttributes redirectAttributes) {
        NivelRiesgo nivelRiesgo = null;

        // Validar que exista el nivel de riesgo
        if (idNivelRiesgo > 0) {
            nivelRiesgo = new NivelRiesgo();
            nivelRiesgo.setIdNivelRiesgo(idNivelRiesgo);
            nivelRiesgo = nivelRiesgoService.buscarNivelRiesgo(nivelRiesgo);

            if (nivelRiesgo == null) {
                redirectAttributes.addFlashAttribute("error", "El nivel de riesgo: " + idNivelRiesgo + " no existe");
                
                return "redirect:" + RUTA_VISTA;
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "No se encontró el nivel de riesgo: " + idNivelRiesgo);
            
            return "redirect:" + RUTA_VISTA;
        }

        model.addAttribute("titulo", "Editar Nivel de Riesgo");
        model.addAttribute("nivelRiesgo", nivelRiesgo);

        return RUTA_VISTA + "crearNivelRiesgo";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/eliminar/{idNivelRiesgo}")
    public String eliminarImpacto(@PathVariable("idNivelRiesgo") Long idNivelRiesgo, RedirectAttributes redirectAttributes) {
        NivelRiesgo nivelRiesgo = null;

        // Validar que exista el nivel de riesgo
        if (idNivelRiesgo > 0) {
            nivelRiesgo = new NivelRiesgo();
            nivelRiesgo.setIdNivelRiesgo(idNivelRiesgo);
            nivelRiesgo = nivelRiesgoService.buscarNivelRiesgo(nivelRiesgo);

            if (nivelRiesgo == null) {
                redirectAttributes.addFlashAttribute("error", "El nivel de riesgo: " + idNivelRiesgo + " no existe");
                
                return "redirect:" + RUTA_VISTA;
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "No se encontró el nivel de riesgo: " + idNivelRiesgo);
            
            return "redirect:" + RUTA_VISTA;
        }

        nivelRiesgoService.eliminarNivelRiesgo(nivelRiesgo);

        redirectAttributes.addFlashAttribute("success", "Nivel de Riesgo: " + nivelRiesgo.getCodigo() + " eliminado exitosamente");
        
        return "redirect:" + RUTA_VISTA;
    }
    
}
