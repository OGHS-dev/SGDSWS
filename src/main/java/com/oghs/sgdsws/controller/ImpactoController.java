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

import com.oghs.sgdsws.model.entity.Impacto;
import com.oghs.sgdsws.model.service.ImpactoService;

import jakarta.validation.Valid;

/**
 *
 * @author oghs
 */
@Controller
@RequestMapping("/views/impactos")
public class ImpactoController {

    private final String RUTA_VISTA = "/views/impactos/";

    @Autowired
    private ImpactoService impactoService;

    @Secured("ROLE_ADMIN")
    @GetMapping("/")
    public String verImpactos(@RequestParam(value = "numeroPagina", required = false, defaultValue = "1") int numeroPagina, @RequestParam(value = "tamano", required = false, defaultValue = "5") int tamano, Model model) {
        model.addAttribute("titulo", "Impactos");
        model.addAttribute("impactos", impactoService.obtenerImpactosPaginado(numeroPagina, tamano));

        return RUTA_VISTA + "verImpactos";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/crear")
    public String crearImpacto(Model model) {
        model.addAttribute("titulo", "Nuevo Impacto");
        model.addAttribute("impacto", new Impacto());

        return RUTA_VISTA + "crearImpacto";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/guardar")
    public String guardarImpacto(@Valid @ModelAttribute Impacto impacto, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        // Validar errores en el formulario
        if (bindingResult.hasErrors()) {
            model.addAttribute("titulo", "Crear/Editar Impacto");
            model.addAttribute("impacto", impacto);

            System.err.println("Error en los datos proporcionados");

            return RUTA_VISTA + "crearImpacto";
        }

        impactoService.guardarImpacto(impacto);

        redirectAttributes.addFlashAttribute("success", "Impacto: " + impacto.getCodigo() + " guardado exitosamente");

        return "redirect:" + RUTA_VISTA;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/editar/{idImpacto}")
    public String editarImpacto(@PathVariable("idImpacto") Long idImpacto, Model model, RedirectAttributes redirectAttributes) {
        Impacto impacto = null;

        // Validar que exista el impacto
        if (idImpacto > 0) {
            impacto = new Impacto();
            impacto.setIdImpacto(idImpacto);
            impacto = impactoService.buscarImpacto(impacto);

            if (impacto == null) {
                redirectAttributes.addFlashAttribute("error", "El impacto: " + idImpacto + " no existe");
                
                return "redirect:" + RUTA_VISTA;
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "No se encontró el impacto: " + idImpacto);
            
            return "redirect:" + RUTA_VISTA;
        }

        model.addAttribute("titulo", "Editar Impacto");
        model.addAttribute("impacto", impacto);

        return RUTA_VISTA + "crearImpacto";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/eliminar/{idImpacto}")
    public String eliminarImpacto(@PathVariable("idImpacto") Long idImpacto, RedirectAttributes redirectAttributes) {
        Impacto impacto = null;

        // Validar que exista el impacto
        if (idImpacto > 0) {
            impacto = new Impacto();
            impacto.setIdImpacto(idImpacto);
            impacto = impactoService.buscarImpacto(impacto);

            if (impacto == null) {
                redirectAttributes.addFlashAttribute("error", "El impacto: " + idImpacto + " no existe");
                
                return "redirect:" + RUTA_VISTA;
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "No se encontró el impacto: " + idImpacto);
            
            return "redirect:" + RUTA_VISTA;
        }

        impactoService.eliminarImpacto(impacto);

        redirectAttributes.addFlashAttribute("success", "Impacto: " + impacto.getCodigo() + " eliminado exitosamente");
        
        return "redirect:" + RUTA_VISTA;
    }
    
}
