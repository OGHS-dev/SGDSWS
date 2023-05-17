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

    @Autowired
    private NivelRiesgoService nivelRiesgoService;

    @Secured("ROLE_ADMIN")
    @GetMapping("/")
    public String verNivelesRiesgo(@RequestParam(value = "numeroPagina", required = false, defaultValue = "1") int numeroPagina, @RequestParam(value = "tamano", required = false, defaultValue = "5") int tamano, Model model) {
        model.addAttribute("titulo", "Niveles de Riesgo");
        model.addAttribute("nivelesRiesgo", nivelRiesgoService.obtenerNivelesRiesgoPaginado(numeroPagina, tamano));

        return "/views/nivelesRiesgo/verNivelesRiesgo";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/crear")
    public String crearNivelRiesgo(Model model) {
        model.addAttribute("titulo", "Nuevo Nivel de Riesgo");
        model.addAttribute("nivelRiesgo", new NivelRiesgo());

        return "/views/nivelesRiesgo/crearNivelRiesgo";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/guardar")
    public String guardarNivelRiesgo(@Valid @ModelAttribute NivelRiesgo nivelRiesgo, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        // Validar errores en el formulario
        if (bindingResult.hasErrors()) {
            model.addAttribute("titulo", "Editar Nivel de Riesgo");
            model.addAttribute("nivelRiesgo", nivelRiesgo);

            System.err.println("Error en los datos proporcionados");
            return "/views/nivelesRiesgo/crearNivelRiesgo";
        }

        nivelRiesgoService.guardarNivelRiesgo(nivelRiesgo);

        redirectAttributes.addFlashAttribute("success", "Nivel de Riesgo: " + nivelRiesgo.getIdNivelRiesgo() + " guardado exitosamente");
        return "redirect:/views/nivelesRiesgo/";
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
                return "redirect:/views/nivelesRiesgo/";
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "No se encontró el nivel de riesgo: " + idNivelRiesgo);
            return "redirect:/views/nivelesRiesgo/";
        }

        model.addAttribute("titulo", "Editar Nivel de Riesgo");
        model.addAttribute("nivelRiesgo", nivelRiesgo);

        return "/views/nivelesRiesgo/crearNivelRiesgo";
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
                return "redirect:/views/nivelesRiesgo/";
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "No se encontró el nivel de riesgo: " + idNivelRiesgo);
            return "redirect:/views/nivelesRiesgo/";
        }

        nivelRiesgoService.eliminarNivelRiesgo(nivelRiesgo);

        redirectAttributes.addFlashAttribute("success", "Nivel de Riesgo: " + nivelRiesgo.getIdNivelRiesgo() + " eliminado exitosamente");
        return "redirect:/views/nivelesRiesgo/";
    }
    
}
