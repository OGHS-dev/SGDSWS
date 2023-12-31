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

import com.oghs.sgdsws.model.entity.Categoria;
import com.oghs.sgdsws.service.CategoriaService;

import jakarta.validation.Valid;

/**
 * CategoriaController es la clase controlador para ejecutar las
 * operaciones CRUD de categorías.
 * 
 * @author oghs
 * @version 1.0
 */
@Controller
@RequestMapping("/views/categorias")
public class CategoriaController {

    private static final String RUTA_VISTA = "/views/categorias/";
    
    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPERVISOR", "ROLE_AUDITOR", "ROLE_REVISOR"})
    @GetMapping("/")
    public String verCategorias(@RequestParam(value = "numeroPagina", required = false, defaultValue = "1") int numeroPagina, @RequestParam(value = "tamano", required = false, defaultValue = "5") int tamano, Model model) {
        model.addAttribute("titulo", "Categorías");
        model.addAttribute("categorias", categoriaService.obtenerCategoriasPaginado(numeroPagina, tamano));

        return RUTA_VISTA + "verCategorias";
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPERVISOR", "ROLE_AUDITOR"})
    @GetMapping("/crear")
    public String crearCategoria(Model model) {
        model.addAttribute("titulo", "Nueva Categoría");
        model.addAttribute("categoria", new Categoria());

        return RUTA_VISTA + "crearCategoria";
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPERVISOR", "ROLE_AUDITOR"})
    @PostMapping("/guardar")
    public String guardarCategoria(@Valid @ModelAttribute Categoria categoria, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        // Validar errores en el formulario
        if (bindingResult.hasErrors()) {
            model.addAttribute("titulo", "Crear/Editar Categoría");
            model.addAttribute("categoria", categoria);

            String errores = "Error en los datos proporcionados:\n\n" + bindingResult.getFieldErrors().stream().map(error -> error.getDefaultMessage() + "\n").collect(Collectors.joining());
            model.addAttribute("warning", errores);

            return RUTA_VISTA + "crearCategoria";
        }

        categoriaService.guardarCategoria(categoria);

        redirectAttributes.addFlashAttribute("success", String.format("Categoría: %s guardada exitosamente", categoria.getCodigo()));

        return "redirect:" + RUTA_VISTA;
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPERVISOR"})
    @GetMapping("/editar/{idCategoria}")
    public String editarCategoria(@PathVariable("idCategoria") Long idCategoria, Model model, RedirectAttributes redirectAttributes) {
        // Validar que exista la categoría
        Categoria categoria = this.validarCategoria(idCategoria);

        if (Objects.isNull(categoria)) {
            redirectAttributes.addFlashAttribute("error", String.format("La categoría: %d no existe", idCategoria));
            
            return "redirect:" + RUTA_VISTA;
        }
        
        model.addAttribute("titulo", "Editar Categoría");
        model.addAttribute("categoria", categoria);

        return RUTA_VISTA + "crearCategoria";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/eliminar/{idCategoria}")
    public String eliminarCategoria(@PathVariable("idCategoria") Long idCategoria, RedirectAttributes redirectAttributes) {
        // Validar que exista la categoría
        Categoria categoria = this.validarCategoria(idCategoria);

        if (Objects.isNull(categoria)) {
            redirectAttributes.addFlashAttribute("error", String.format("La categoría: %d no existe", idCategoria));
        } else {
            if (categoria.getBitacoraProyecto().isEmpty()) {
                categoriaService.eliminarCategoria(categoria);

                redirectAttributes.addFlashAttribute("success", String.format("Categoría: %s eliminada exitosamente", categoria.getCodigo()));
            } else {
                String bitacoraProyecto = categoria.getBitacoraProyecto().stream().map(bp -> bp.getDescripcion() + ", ").collect(Collectors.joining());
                redirectAttributes.addFlashAttribute("warning", String.format("La categoria: %s no se puede eliminar ya que se encuentra asociada a los siguientes eventos: %s", categoria.getCodigo(), bitacoraProyecto));
            }
        }
        
        return "redirect:" + RUTA_VISTA;
    }

    /**
     * Retorna un objeto Categoria si ésta existe y se encontró.
     *
     * @param idCategoria el id de la categoría a buscar
     * @return categoria el objeto de la categoría encontrada
     */
    private Categoria validarCategoria(Long idCategoria) {
        Categoria categoria = null;

        if (idCategoria > 0) {
            categoria = new Categoria();
            categoria.setIdCategoria(idCategoria);
            categoria = categoriaService.buscarCategoria(categoria);

            // No se encontró la categoría
            if (Objects.isNull(categoria)) {
                return null;
            }
        }

        return categoria;
    }
}
