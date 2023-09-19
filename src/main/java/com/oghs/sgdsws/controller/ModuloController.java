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

import com.oghs.sgdsws.model.entity.Modulo;
import com.oghs.sgdsws.service.ModuloService;

import jakarta.validation.Valid;

/**
 * ModuloController es la clase controlador para ejecutar las
 * operaciones CRUD de modulos.
 * 
 * @author oghs
 * @version 1.0
 */
@Controller
@RequestMapping("/views/modulos")
public class ModuloController {
    
    private final String RUTA_VISTA = "/views/modulos/";

    @Autowired
    private ModuloService moduloService;

    @Secured("ROLE_ADMIN")
    @GetMapping("/")
    public String verModulos(@RequestParam(value = "numeroPagina", required = false, defaultValue = "1") int numeroPagina, @RequestParam(value = "tamano", required = false, defaultValue = "5") int tamano, Model model) {
        model.addAttribute("titulo", "Módulos");
        model.addAttribute("modulos", moduloService.obtenerModulosPaginado(numeroPagina, tamano));

        return RUTA_VISTA + "verModulos";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/crear")
    public String crearModulo(Model model) {
        model.addAttribute("titulo", "Nuevo Módulo");
        model.addAttribute("modulo", new Modulo());

        return RUTA_VISTA + "crearModulo";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/guardar")
    public String guardarModulo(@Valid @ModelAttribute Modulo modulo, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        // Validar errores en el formulario
        if (bindingResult.hasErrors()) {
            model.addAttribute("titulo", "Crear/Editar Módulo");
            model.addAttribute("modulo", new Modulo());

            String errores = "Error en los datos proporcionados:\n\n" + bindingResult.getFieldErrors().stream().map(error -> error.getDefaultMessage() + "\n").collect(Collectors.joining());
            model.addAttribute("warning", errores);
            
            return RUTA_VISTA + "crearModulo";
        }

        moduloService.guardarModulo(modulo);

        redirectAttributes.addFlashAttribute("success", String.format("Módulo: %s guardado exitosamente", modulo.getCodigo()));

        return "redirect:" + RUTA_VISTA;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/editar/{idModulo}")
    public String editarModulo(@PathVariable("idModulo") Long idModulo, Model model, RedirectAttributes redirectAttributes) {
        // Validar que exista el módulo
        Modulo modulo = this.validarModulo(idModulo);

        if (Objects.isNull(modulo)) {
            redirectAttributes.addFlashAttribute("error", String.format("El módulo: %d no existe", idModulo));
            
            return "redirect:" + RUTA_VISTA;
        }

        model.addAttribute("titulo", "Editar Módulo");
        model.addAttribute("modulo", modulo);

        return RUTA_VISTA + "crearModulo";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/eliminar/{idModulo}")
    public String eliminarModulo(@PathVariable("idModulo") Long idModulo, RedirectAttributes redirectAttributes) {
        // Validar que exista el módulo
        Modulo modulo = this.validarModulo(idModulo);

        if (Objects.isNull(modulo)) {
            redirectAttributes.addFlashAttribute("error", String.format("El módulo: %d no existe", idModulo));
        } else {
            moduloService.eliminarModulo(modulo);
            
            redirectAttributes.addFlashAttribute("success", String.format("Módulo: %s eliminado exitosamente", modulo.getCodigo()));
        }

        return "redirect:" + RUTA_VISTA;
    }

    /**
     * Retorna un objeto Modulo si éste existe y se encontró.
     *
     * @param idModulo el id del módulo a buscar
     * @return modulo el objeto del módulo encontrado
     */
    private Modulo validarModulo(Long idModulo) {
        Modulo modulo = null;

        if (idModulo > 0) {
            modulo = new Modulo();
            modulo.setIdModulo(idModulo);
            modulo = moduloService.buscarModulo(modulo);

            // No se encontró el modulo
            if (Objects.isNull(modulo)) {
                return null;
            }
        }

        return modulo;
    }
}
