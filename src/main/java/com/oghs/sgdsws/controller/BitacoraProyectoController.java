package com.oghs.sgdsws.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.oghs.sgdsws.model.ProyectoDTO;
import com.oghs.sgdsws.model.entity.Proyecto;
import com.oghs.sgdsws.model.service.BitacoraProyectoService;
import com.oghs.sgdsws.model.service.EstadoBitacoraProyectoService;
import com.oghs.sgdsws.model.service.EstadoProyectoService;
import com.oghs.sgdsws.model.service.EventoService;
import com.oghs.sgdsws.model.service.ImpactoService;
import com.oghs.sgdsws.model.service.NivelRiesgoService;
import com.oghs.sgdsws.model.service.PrioridadService;
import com.oghs.sgdsws.model.service.ProyectoService;
import com.oghs.sgdsws.model.service.UsuarioService;

import jakarta.validation.Valid;

/**
 *
 * @author oghs
 */
@Controller
@RequestMapping("/views/proyectos/detalle")
public class BitacoraProyectoController {

    private final String RUTA_VISTA = "/views/proyectos/";

    @Autowired
    private ProyectoService proyectoService;

    @Autowired
    private BitacoraProyectoService bitacoraProyectoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EstadoProyectoService estadoProyectoService;

    @Autowired
    private PrioridadService prioridadService;

    @Autowired
    private ImpactoService impactoService;

    @Autowired
    private NivelRiesgoService nivelRiesgoService;

    @Autowired
    private EventoService eventoService;

    @Autowired
    private EstadoBitacoraProyectoService estadoBitacoraProyectoService;
    
    @Secured({"ROLE_ADMIN", "ROLE_SUPERVISOR", "ROLE_AUDITOR", "ROLE_REVISOR", "ROLE_DESARROLLO"})
    @PostMapping("/guardar")
    public String guardarBitacoraProyecto(@Valid @ModelAttribute ProyectoDTO proyectoDTO, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        // Validar errores en el formulario
        if (bindingResult.hasErrors()) {
            Proyecto proyecto = new Proyecto();
            proyecto.setIdProyecto(proyectoDTO.getProyecto().getIdProyecto());
            proyecto = proyectoService.buscarProyecto(proyecto);

            proyectoDTO.setProyecto(proyecto);
            proyectoDTO.setListaUsuariosProyecto(usuarioService.obtenerUsuariosPorProyecto(proyecto));
            proyectoDTO.setListaBitacoraProyectoPaginado(bitacoraProyectoService.obtenerBitacoraProyectoPorProyectoPaginado(proyecto, 1, 5));

            model.addAttribute("titulo", "Detalle de Proyecto");
            model.addAttribute("proyectoDTO", proyectoDTO);
            model.addAttribute("listaEstadosProyecto", estadoProyectoService.obtenerEstadosProyecto());
            model.addAttribute("listaPrioridades", prioridadService.obtenerPrioridades());
            model.addAttribute("listaImpactos", impactoService.obtenerImpactos());
            model.addAttribute("listaNivelesRiesgo", nivelRiesgoService.obtenerNivelesRiesgo());
            model.addAttribute("listaEventos", eventoService.obtenerEventos());
            model.addAttribute("listaEstadosBitacoraProyecto", estadoBitacoraProyectoService.obtenerEstadosBitacoraProyecto());

            System.err.println("Error en los datos proporcionados");

            return RUTA_VISTA + "detalleProyecto";
        }

        bitacoraProyectoService.guardarBitacoraProyecto(proyectoDTO);

        redirectAttributes.addFlashAttribute("success", "Bitácora Proyecto: " + proyectoDTO.getBitacoraProyecto().getRevision() + " guardada exitosamente");

        return "redirect:" + RUTA_VISTA + "detalle/" + proyectoDTO.getProyecto().getIdProyecto() + "?numeroPagina=1";
    }
}
