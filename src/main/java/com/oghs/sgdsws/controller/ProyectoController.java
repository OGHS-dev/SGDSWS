package com.oghs.sgdsws.controller;

import java.util.List;
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

import com.oghs.sgdsws.model.ProyectoDTO;
import com.oghs.sgdsws.model.entity.Proyecto;
import com.oghs.sgdsws.service.EstadoProyectoService;
import com.oghs.sgdsws.service.ProyectoService;
import com.oghs.sgdsws.service.UsuarioService;

import jakarta.validation.Valid;

/**
 * ProyectoController es la clase controlador para ejecutar las
 * operaciones CRUD de proyectos.
 * 
 * @author oghs
 * @version 1.0
 */
@Controller
@RequestMapping("/views/proyectos")
public class ProyectoController {

    private final String RUTA_VISTA = "/views/proyectos/";

    @Autowired
    private ProyectoService proyectoService;

    @Autowired
    private EstadoProyectoService estadoProyectoService;

    @Autowired
    private UsuarioService usuarioService;

    @Secured({"ROLE_ADMIN", "ROLE_SUPERVISOR", "ROLE_AUDITOR", "ROLE_REVISOR", "ROLE_DESARROLLO"})
    @GetMapping("/")
    public String verProyectos(@RequestParam(value = "numeroPagina", required = false, defaultValue = "1") int numeroPagina, @RequestParam(value = "tamano", required = false, defaultValue = "5") int tamano, Model model) {
        model.addAttribute("titulo", "Proyectos");
        model.addAttribute("navegacion", List.of("Proyectos"));
        model.addAttribute("proyectos", proyectoService.obtenerProyectosPaginado(numeroPagina, tamano));

        return RUTA_VISTA + "verProyectos";
    }
    
    @Secured({"ROLE_ADMIN", "ROLE_SUPERVISOR", "ROLE_AUDITOR", "ROLE_REVISOR"})
    @GetMapping("/crear")
    public String crearProyecto(Model model) {
        model.addAttribute("titulo", "Nuevo Proyecto");
        model.addAttribute("navegacion", List.of("Proyectos", "Crear Proyecto"));
        model.addAttribute("proyectoDTO", new ProyectoDTO());
        model.addAttribute("listaUsuarios", usuarioService.obtenerUsuarios());
        model.addAttribute("listaEstadosProyecto", estadoProyectoService.obtenerEstadosProyecto());
        
        return RUTA_VISTA + "crearProyecto";
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPERVISOR", "ROLE_AUDITOR", "ROLE_REVISOR"})
    @PostMapping("/guardar")
    public String guardarProyecto(@Valid @ModelAttribute ProyectoDTO proyectoDTO, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        // Validar errores en el formulario
        if (bindingResult.hasErrors()) {
            model.addAttribute("titulo", "Crear/Editar Proyecto");
            model.addAttribute("proyectoDTO", proyectoDTO);
            model.addAttribute("listaUsuarios", usuarioService.obtenerUsuarios());
            model.addAttribute("listaEstadosProyecto", estadoProyectoService.obtenerEstadosProyecto());

            String errores = "Error en los datos proporcionados:\n\n" + bindingResult.getFieldErrors().stream().map(error -> error.getDefaultMessage() + "\n").collect(Collectors.joining());
            model.addAttribute("warning", errores);
            
            return RUTA_VISTA + "crearProyecto";
        }

        proyectoService.guardarProyecto(proyectoDTO);

        redirectAttributes.addFlashAttribute("success", String.format("Proyecto: %s guardado exitosamente", proyectoDTO.getProyecto().getNombre()));

        return "redirect:" + RUTA_VISTA;
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPERVISOR", "ROLE_AUDITOR", "ROLE_REVISOR"})
    @GetMapping("/editar/{idProyecto}")
    public String editarProyecto(@PathVariable("idProyecto") Long idProyecto, Model model, RedirectAttributes redirectAttributes) {
        // Validar que exista el proyecto
        Proyecto proyecto = this.validarProyecto(idProyecto);

        if (Objects.isNull(proyecto)) {
            redirectAttributes.addFlashAttribute("error", String.format("El proyecto: %d no existe", idProyecto));
            
            return "redirect:" + RUTA_VISTA;
        }

        ProyectoDTO proyectoDTO = new ProyectoDTO();
        proyectoDTO.setProyecto(proyecto);
        proyectoDTO.setListaUsuariosProyecto(usuarioService.obtenerUsuariosPorProyecto(proyecto));

        model.addAttribute("titulo", "Editar Proyecto");
        model.addAttribute("proyectoDTO", proyectoDTO);
        model.addAttribute("listaUsuarios", usuarioService.obtenerUsuarios());
        model.addAttribute("listaEstadosProyecto", estadoProyectoService.obtenerEstadosProyecto());

        return RUTA_VISTA + "crearProyecto";
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPERVISOR", "ROLE_AUDITOR", "ROLE_REVISOR"})
    @GetMapping("/eliminar/{idProyecto}")
    public String eliminarProyecto(@PathVariable("idProyecto") Long idProyecto, RedirectAttributes redirectAttributes) {
        // Validar que exista el proyecto
        Proyecto proyecto = this.validarProyecto(idProyecto);

        if (Objects.isNull(proyecto)) {
            redirectAttributes.addFlashAttribute("error", String.format("El proyecto: %d no existe", idProyecto));
        } else {
            proyectoService.eliminarProyecto(proyecto);

            redirectAttributes.addFlashAttribute("success", String.format("Proyecto: %s eliminado exitosamente", proyecto.getNombre()));
        }

        return "redirect:" + RUTA_VISTA;
    }
    
    /**
     * Retorna un objeto Proyecto si éste existe y se encontró.
     *
     * @param idProyecto el id del proyecto a buscar
     * @return proyecto el objeto del proyecto encontrado
     */
    private Proyecto validarProyecto(Long idProyecto) {
        Proyecto proyecto = null;

        if (idProyecto > 0) {
            proyecto = new Proyecto();
            proyecto.setIdProyecto(idProyecto);
            proyecto = proyectoService.buscarProyecto(proyecto);

            // No se encontró el proyecto
            if (Objects.isNull(proyecto)) {
                return null;
            }
        }

        return proyecto;
    }

}
