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

import com.oghs.sgdsws.model.entity.Categoria;
import com.oghs.sgdsws.model.service.CategoriaService;

import jakarta.validation.Valid;

/**
 *
 * @author oghs
 */
@Controller
@RequestMapping("/views/categorias")
public class CategoriaController {

    private final String RUTA_VISTA = "/views/categorias/";
    
    @Autowired
    private CategoriaService categoriaService;
    
    @Secured("ROLE_ADMIN")
    @GetMapping("/")
    public String verCategorias(@RequestParam(value = "numeroPagina", required = false, defaultValue = "1") int numeroPagina, @RequestParam(value = "tamano", required = false, defaultValue = "5") int tamano, Model model) {
        model.addAttribute("titulo", "Categorías");
        model.addAttribute("categorias", categoriaService.obtenerCategoriasPaginado(numeroPagina, tamano));

        return RUTA_VISTA + "verCategorias";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/crear")
    public String crearCategoria(Model model) {
        model.addAttribute("titulo", "Nueva Categoría");
        model.addAttribute("categoria", new Categoria());

        return RUTA_VISTA + "crearCategoria";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/guardar")
    public String guardarCategoria(@Valid @ModelAttribute Categoria categoria, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        // Validar errores en el formulario
        if (bindingResult.hasErrors()) {
            model.addAttribute("titulo", "Crear/Editar Categoría");
            model.addAttribute("categoria", categoria);

            System.err.println("Error en los datos proporcionados");

            return RUTA_VISTA + "crearCategoria";
        }

        categoriaService.guardarCategoria(categoria);

        redirectAttributes.addFlashAttribute("success", "Categoría: " + categoria.getCodigo() + " guardada exitosamente");

        return "redirect:" + RUTA_VISTA;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/editar/{idCategoria}")
    public String editarCategoria(@PathVariable("idCategoria") Long idCategoria, Model model, RedirectAttributes redirectAttributes) {
        Categoria categoria = null;

        // Validar que exista la categoria
        if (idCategoria > 0) {
            categoria = new Categoria();
            categoria.setIdCategoria(idCategoria);
            categoria = categoriaService.buscarCategoria(categoria);

            if (categoria == null) {
                redirectAttributes.addFlashAttribute("error", "La categoría: " + idCategoria + " no existe");
                
                return "redirect:" + RUTA_VISTA;
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "No se encontró la categoría: " + idCategoria);
            
            return "redirect:" + RUTA_VISTA;
        }

        model.addAttribute("titulo", "Editar Categoría");
        model.addAttribute("categoria", categoria);

        return RUTA_VISTA + "crearCategoria";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/eliminar/{idCategoria}")
    public String eliminarCategoria(@PathVariable("idCategoria") Long idCategoria, RedirectAttributes redirectAttributes) {
        Categoria categoria = null;

        // Validar que exista la categoria
        if (idCategoria > 0) {
            categoria = new Categoria();
            categoria.setIdCategoria(idCategoria);
            categoria = categoriaService.buscarCategoria(categoria);

            if (categoria == null) {
                redirectAttributes.addFlashAttribute("error", "La categoría: " + idCategoria + " no existe");
                
                return "redirect:" + RUTA_VISTA;
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "No se encontró la categoría: " + idCategoria);
            
            return "redirect:" + RUTA_VISTA;
        }

        categoriaService.eliminarCategoria(categoria);

        redirectAttributes.addFlashAttribute("success", "Categoría: " + categoria.getCodigo() + " eliminada exitosamente");
        
        return "redirect:" + RUTA_VISTA;
    }
}
