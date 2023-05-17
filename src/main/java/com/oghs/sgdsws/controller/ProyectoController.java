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

import com.oghs.sgdsws.model.ProyectoDTO;
import com.oghs.sgdsws.model.entity.Proyecto;
import com.oghs.sgdsws.model.service.BitacoraProyectoService;
import com.oghs.sgdsws.model.service.ProyectoService;
import com.oghs.sgdsws.model.service.UsuarioService;

import jakarta.validation.Valid;

/**
 *
 * @author oghs
 */
@Controller
@RequestMapping("/views/proyectos")
public class ProyectoController {

    @Autowired
    private ProyectoService proyectoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private BitacoraProyectoService bitacoraProyectoService;

    @Secured({"ROLE_ADMIN", "ROLE_SUPERVISOR", "ROLE_AUDITOR", "ROLE_REVISOR", "ROLE_DESARROLLO"})
    @GetMapping("/")
    public String verProyectos(@RequestParam(value = "numeroPagina", required = false, defaultValue = "1") int numeroPagina, @RequestParam(value = "tamano", required = false, defaultValue = "5") int tamano, Model model) {
        model.addAttribute("titulo", "Proyectos");
        model.addAttribute("proyectos", proyectoService.obtenerProyectosPaginado(numeroPagina, tamano));

        return "/views/proyectos/verProyectos";
    }
    
    @Secured({"ROLE_ADMIN", "ROLE_SUPERVISOR", "ROLE_AUDITOR", "ROLE_REVISOR"})
    @GetMapping("/crear")
    public String crearProyecto(Model model) {
        model.addAttribute("titulo", "Nuevo Proyecto");
        model.addAttribute("proyectoDTO", new ProyectoDTO());
        model.addAttribute("listaUsuarios", usuarioService.obtenerUsuarios());
        
        return "/views/proyectos/crearProyecto";
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPERVISOR", "ROLE_AUDITOR", "ROLE_REVISOR"})
    @PostMapping("/guardar")
    public String guardarProyecto(@Valid @ModelAttribute ProyectoDTO proyectoDTO, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        // Validar errores en el formulario
        if (bindingResult.hasErrors()) {
            model.addAttribute("titulo", "Editar Proyecto");
            model.addAttribute("proyectoDTO", proyectoDTO);

            System.err.println("Error en los datos proporcionados");
            return "/views/proyectos/crearProyecto";
        }

        proyectoService.guardarProyecto(proyectoDTO);
        
        redirectAttributes.addFlashAttribute("success", "Proyecto: " + proyectoDTO.getProyecto().getIdProyecto() + " guardado exitosamente");
        return "redirect:/views/proyectos/";
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPERVISOR", "ROLE_AUDITOR", "ROLE_REVISOR"})
    @GetMapping("/editar/{idProyecto}")
    public String editarProyecto(@PathVariable("idProyecto") Long idProyecto, Model model, RedirectAttributes redirectAttributes) {
        Proyecto proyecto = null;

        // Validar que exista el proyecto
        if (idProyecto > 0) {
            proyecto = new Proyecto();
            proyecto.setIdProyecto(idProyecto);
            proyecto = proyectoService.buscarProyecto(proyecto);

            if (proyecto == null) {
                redirectAttributes.addFlashAttribute("error", "El proyecto: " + idProyecto + " no existe");
                return "redirect:/views/proyectos/";
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "No se encontró el proyecto: " + idProyecto);
            return "redirect:/views/proyectos/";
        }

        ProyectoDTO proyectoDTO = new ProyectoDTO();
        proyectoDTO.setProyecto(proyecto);
        proyectoDTO.setListaUsuariosProyecto(usuarioService.obtenerUsuariosPorProyecto(proyecto));

        model.addAttribute("titulo", "Editar Proyecto");
        model.addAttribute("proyectoDTO", proyectoDTO);
        model.addAttribute("listaUsuarios", usuarioService.obtenerUsuarios());

        return "/views/proyectos/crearProyecto";
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPERVISOR", "ROLE_AUDITOR", "ROLE_REVISOR"})
    @GetMapping("/eliminar/{idProyecto}")
    public String eliminarProyecto(@PathVariable("idProyecto") Long idProyecto, RedirectAttributes redirectAttributes) {
        Proyecto proyecto = null;

        // Validar que exista el proyecto
        if (idProyecto > 0) {
            proyecto = new Proyecto();
            proyecto.setIdProyecto(idProyecto);
            proyecto = proyectoService.buscarProyecto(proyecto);

            if (proyecto == null) {
                redirectAttributes.addFlashAttribute("error", "El proyecto: " + idProyecto + " no existe");
                return "redirect:/views/proyectos/";
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "No se encontró el proyecto: " + idProyecto);
            return "redirect:/views/proyectos/";
        }

        proyectoService.eliminarProyecto(proyecto);

        redirectAttributes.addFlashAttribute("success", "Proyecto: " + proyecto.getIdProyecto() + " eliminado exitosamente");
        return "redirect:/views/proyectos/";
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPERVISOR", "ROLE_AUDITOR", "ROLE_REVISOR", "ROLE_DESARROLLO"})
    @GetMapping("/detalle/{idProyecto}")
    public String detalleProyecto(@PathVariable("idProyecto") Long idProyecto, @RequestParam(value = "numeroPagina", required = false, defaultValue = "1") int numeroPagina, @RequestParam(value = "tamano", required = false, defaultValue = "5") int tamano, Model model, RedirectAttributes redirectAttributes) {
        Proyecto proyecto = null;

        // Validar que exista el proyecto
        if (idProyecto > 0) {
            proyecto = new Proyecto();
            proyecto.setIdProyecto(idProyecto);
            proyecto = proyectoService.buscarProyecto(proyecto);

            if (proyecto == null) {
                redirectAttributes.addFlashAttribute("error", "El proyecto: " + idProyecto + " no existe");
                return "redirect:/views/proyectos/";
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "No se encontró el proyecto: " + idProyecto);
            return "redirect:/views/proyectos/";
        }

        ProyectoDTO proyectoDTO = new ProyectoDTO();
        proyectoDTO.setProyecto(proyecto);
        proyectoDTO.setListaUsuariosProyecto(usuarioService.obtenerUsuarios());
        proyectoDTO.setListaBitacoraProyectoPaginado(bitacoraProyectoService.obtenerBitacoraProyectoPorProyectoPaginado(proyecto, numeroPagina, tamano));

        model.addAttribute("titulo", "Detalle de Proyecto");
        model.addAttribute("proyectoDTO", proyectoDTO);

        return "/views/proyectos/detalleProyecto";
    }
}
