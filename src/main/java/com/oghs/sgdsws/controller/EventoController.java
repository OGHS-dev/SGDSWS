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

import com.oghs.sgdsws.model.entity.Evento;
import com.oghs.sgdsws.model.service.EventoService;

import jakarta.validation.Valid;

/**
 *
 * @author oghs
 */
@Controller
@RequestMapping("/views/eventos")
public class EventoController {

    private final String RUTA_VISTA = "/views/eventos/";
    
    @Autowired
    private EventoService eventoService;
    
    @Secured("ROLE_ADMIN")
    @GetMapping("/")
    public String verEventos(@RequestParam(value = "numeroPagina", required = false, defaultValue = "1") int numeroPagina, @RequestParam(value = "tamano", required = false, defaultValue = "5") int tamano, Model model) {
        model.addAttribute("titulo", "Eventos");
        model.addAttribute("eventos", eventoService.obtenerEventosPaginado(numeroPagina, tamano));

        return RUTA_VISTA + "verEventos";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/crear")
    public String crearEvento(Model model) {
        model.addAttribute("titulo", "Nuevo Evento");
        model.addAttribute("evento", new Evento());

        return RUTA_VISTA + "crearEvento";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/guardar")
    public String guardarEvento(@Valid @ModelAttribute Evento evento, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        // Validar errores en el formulario
        if (bindingResult.hasErrors()) {
            model.addAttribute("titulo", "Crear/Editar Evento");
            model.addAttribute("evento", evento);

            System.err.println("Error en los datos proporcionados");

            return RUTA_VISTA + "crearEvento";
        }

        eventoService.guardarEvento(evento);

        redirectAttributes.addFlashAttribute("success", "Evento: " + evento.getCodigo() + " guardado exitosamente");

        return "redirect:" + RUTA_VISTA;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/editar/{idEvento}")
    public String editarEvento(@PathVariable("idEvento") Long idEvento, Model model, RedirectAttributes redirectAttributes) {
        Evento evento = null;

        // Validar que exista el evento
        if (idEvento > 0) {
            evento = new Evento();
            evento.setIdEvento(idEvento);
            evento = eventoService.buscarEvento(evento);

            if (evento == null) {
                redirectAttributes.addFlashAttribute("error", "El evento: " + idEvento + " no existe");
                
                return "redirect:" + RUTA_VISTA;
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "No se encontró el evento: " + idEvento);
            
            return "redirect:" + RUTA_VISTA;
        }

        model.addAttribute("titulo", "Editar Evento");
        model.addAttribute("evento", evento);

        return RUTA_VISTA + "crearEvento";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/eliminar/{idEvento}")
    public String eliminarEvento(@PathVariable("idEvento") Long idEvento, RedirectAttributes redirectAttributes) {
        Evento evento = null;

        // Validar que exista el evento
        if (idEvento > 0) {
            evento = new Evento();
            evento.setIdEvento(idEvento);
            evento = eventoService.buscarEvento(evento);

            if (evento == null) {
                redirectAttributes.addFlashAttribute("error", "El evento: " + idEvento + " no existe");
                
                return "redirect:" + RUTA_VISTA;
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "No se encontró el evento: " + idEvento);
            
            return "redirect:" + RUTA_VISTA;
        }

        eventoService.eliminarEvento(evento);

        redirectAttributes.addFlashAttribute("success", "Evento: " + evento.getCodigo() + " eliminado exitosamente");
        
        return "redirect:" + RUTA_VISTA;
    }
}
