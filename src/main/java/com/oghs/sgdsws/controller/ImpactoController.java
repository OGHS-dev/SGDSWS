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

import com.oghs.sgdsws.model.entity.Impacto;
import com.oghs.sgdsws.service.ImpactoService;

import jakarta.validation.Valid;

/**
 * ImpactoController es la clase controlador para ejecutar las
 * operaciones CRUD de impactos.
 * 
 * @author oghs
 * @version 1.0
 */
@Controller
@RequestMapping("/views/impactos")
public class ImpactoController {

    private static final String RUTA_VISTA = "/views/impactos/";

    private final ImpactoService impactoService;

    public ImpactoController(ImpactoService impactoService) {
        this.impactoService = impactoService;
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPERVISOR", "ROLE_AUDITOR", "ROLE_REVISOR"})
    @GetMapping("/")
    public String verImpactos(@RequestParam(value = "numeroPagina", required = false, defaultValue = "1") int numeroPagina, @RequestParam(value = "tamano", required = false, defaultValue = "5") int tamano, Model model) {
        model.addAttribute("titulo", "Impactos");
        model.addAttribute("impactos", impactoService.obtenerImpactosPaginado(numeroPagina, tamano));

        return RUTA_VISTA + "verImpactos";
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPERVISOR", "ROLE_AUDITOR"})
    @GetMapping("/crear")
    public String crearImpacto(Model model) {
        model.addAttribute("titulo", "Nuevo Impacto");
        model.addAttribute("impacto", new Impacto());

        return RUTA_VISTA + "crearImpacto";
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPERVISOR", "ROLE_AUDITOR"})
    @PostMapping("/guardar")
    public String guardarImpacto(@Valid @ModelAttribute Impacto impacto, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        // Validar errores en el formulario
        if (bindingResult.hasErrors()) {
            model.addAttribute("titulo", "Crear/Editar Impacto");
            model.addAttribute("impacto", impacto);

            String errores = "Error en los datos proporcionados:\n\n" + bindingResult.getFieldErrors().stream().map(error -> error.getDefaultMessage() + "\n").collect(Collectors.joining());
            model.addAttribute("warning", errores);

            return RUTA_VISTA + "crearImpacto";
        }

        impactoService.guardarImpacto(impacto);

        redirectAttributes.addFlashAttribute("success", String.format("Impacto: %s guardado exitosamente", impacto.getCodigo()));

        return "redirect:" + RUTA_VISTA;
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPERVISOR"})
    @GetMapping("/editar/{idImpacto}")
    public String editarImpacto(@PathVariable("idImpacto") Long idImpacto, Model model, RedirectAttributes redirectAttributes) {
        // Validar que exista el impacto
        Impacto impacto = this.validarImpacto(idImpacto);

        if (Objects.isNull(impacto)) {
            redirectAttributes.addFlashAttribute("error", String.format("El impacto: %d no existe", idImpacto));
            
            return "redirect:" + RUTA_VISTA;
        }

        model.addAttribute("titulo", "Editar Impacto");
        model.addAttribute("impacto", impacto);

        return RUTA_VISTA + "crearImpacto";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/eliminar/{idImpacto}")
    public String eliminarImpacto(@PathVariable("idImpacto") Long idImpacto, RedirectAttributes redirectAttributes) {
        // Validar que exista el impacto
        Impacto impacto = this.validarImpacto(idImpacto);

        if (Objects.isNull(impacto)) {
            redirectAttributes.addFlashAttribute("error", String.format("El impacto: %d no existe", idImpacto));
        } else {
            if (impacto.getBitacoraProyecto().isEmpty()) {
                impactoService.eliminarImpacto(impacto);

                redirectAttributes.addFlashAttribute("success", String.format("Impacto: %s eliminado exitosamente", impacto.getCodigo()));
            } else {
                String bitacoraProyecto = impacto.getBitacoraProyecto().stream().map(bp -> bp.getDescripcion() + ", ").collect(Collectors.joining());
                redirectAttributes.addFlashAttribute("warning", String.format("El impacto: %s no se puede eliminar ya que se encuentra asociado a los siguientes eventos: %s", impacto.getCodigo(), bitacoraProyecto));
            }
        }
        
        return "redirect:" + RUTA_VISTA;
    }
    
    /**
     * Retorna un objeto Impacto si éste existe y se encontró.
     *
     * @param idUsuario el id del impacto a buscar
     * @return impacto el objeto del impacto encontrado
     */
    private Impacto validarImpacto(Long idImpacto) {
        Impacto impacto = null;

        if (idImpacto > 0) {
            impacto = new Impacto();
            impacto.setIdImpacto(idImpacto);
            impacto = impactoService.buscarImpacto(impacto);

            // No se encontró el impacto
            if (Objects.isNull(impacto)) {
                return null;
            }
        }

        return impacto;
    }
}
