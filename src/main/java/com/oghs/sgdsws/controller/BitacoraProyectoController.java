package com.oghs.sgdsws.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.oghs.sgdsws.model.ProyectoDTO;
import com.oghs.sgdsws.model.entity.Archivo;
import com.oghs.sgdsws.model.entity.BitacoraProyecto;
import com.oghs.sgdsws.model.entity.Proyecto;
import com.oghs.sgdsws.model.service.ArchivoService;
import com.oghs.sgdsws.model.service.BitacoraProyectoService;
import com.oghs.sgdsws.model.service.EstadoBitacoraProyectoService;
import com.oghs.sgdsws.model.service.EstadoProyectoService;
import com.oghs.sgdsws.model.service.CategoriaService;
import com.oghs.sgdsws.model.service.ImpactoService;
import com.oghs.sgdsws.model.service.NivelRiesgoService;
import com.oghs.sgdsws.model.service.PrioridadService;
import com.oghs.sgdsws.model.service.ProyectoService;
import com.oghs.sgdsws.model.service.UsuarioService;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
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
    private CategoriaService categoriaService;

    @Autowired
    private EstadoBitacoraProyectoService estadoBitacoraProyectoService;

    @Autowired
    private ArchivoService archivoService;

    @Secured({"ROLE_ADMIN", "ROLE_SUPERVISOR", "ROLE_AUDITOR", "ROLE_REVISOR", "ROLE_DESARROLLO"})
    @GetMapping("/actualizar")
    public String actualizarProyecto(@Param("idProyecto") Long idProyecto, HttpServletResponse httpServletResponse, RedirectAttributes redirectAttributes) throws IOException {
        
        System.out.println(idProyecto);

        return "redirect:" + RUTA_VISTA + "detalle/" + idProyecto + "?numeroPagina=1";
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPERVISOR", "ROLE_AUDITOR", "ROLE_REVISOR", "ROLE_DESARROLLO"})
    @GetMapping("/descargarArchivo")
    public void descargarArchivo(@Param("idArchivo") Long idArchivo, HttpServletResponse httpServletResponse) throws IOException {
        Archivo archivo = new Archivo();
        archivo.setIdArchivo(idArchivo);
        archivo = archivoService.buscarArchivo(archivo);

        if (archivo != null) {
            httpServletResponse.setContentType("application/octet-stream");
            httpServletResponse.setHeader("Content-Disposition", "attachment; filename = " + archivo.getNombreArchivo());
            ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
            servletOutputStream.write(archivo.getArchivo());
            servletOutputStream.close();
        } else {
            System.err.println("El archivo no existe");
        }
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPERVISOR", "ROLE_AUDITOR", "ROLE_REVISOR", "ROLE_DESARROLLO"})
    @GetMapping("/eliminarArchivo")
    public String eliminarArchivo(@Param("idProyecto") Long idProyecto, @Param("idArchivo") Long idArchivo, HttpServletResponse httpServletResponse, RedirectAttributes redirectAttributes) throws IOException {
        Archivo archivo = new Archivo();
        archivo.setIdArchivo(idArchivo);
        archivo = archivoService.buscarArchivo(archivo);

        if (archivo != null) {
            archivoService.eliminarArchivo(archivo);

            redirectAttributes.addFlashAttribute("success", "Archivo: " + archivo.getNombreArchivo() + " eliminado exitosamente");
        } else {
            redirectAttributes.addFlashAttribute("error", "El archivo con ID: " + idArchivo + " no existe");
        }

        return "redirect:" + RUTA_VISTA + "detalle/" + idProyecto + "?numeroPagina=1";
    }
    
    @Secured({"ROLE_ADMIN", "ROLE_SUPERVISOR", "ROLE_AUDITOR", "ROLE_REVISOR", "ROLE_DESARROLLO"})
    @PostMapping("/guardar")
    public String guardarBitacoraProyecto(@Valid @ModelAttribute ProyectoDTO proyectoDTO, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes, @RequestParam("archivos") MultipartFile[] multipartFile) throws IOException {
        // Validar errores en el formulario
        if (bindingResult.hasErrors()) {

            Proyecto proyecto = new Proyecto();
            proyecto.setIdProyecto(proyectoDTO.getBitacoraProyecto().getProyecto().getIdProyecto());
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
            model.addAttribute("listaCategorias", categoriaService.obtenerCategorias());
            model.addAttribute("listaEstadosBitacoraProyecto", estadoBitacoraProyectoService.obtenerEstadosBitacoraProyecto());

            String errores = "Error en los datos proporcionados:\n\n";
            for (FieldError error : bindingResult.getFieldErrors()) {
                errores += error.getDefaultMessage() + "\n";
            }
            model.addAttribute("warning", errores);

            return RUTA_VISTA + "detalleProyecto";
        }

        
        List<Archivo> archivos = new ArrayList<>();
        if (multipartFile.length > 1) {
            System.out.println("llegan archivos");
            for (MultipartFile file : multipartFile) {
                Archivo archivo = new Archivo();
                archivo.setNombreArchivo(file.getOriginalFilename());
                // file.getContentType();
                archivo.setTamanoArchivo(file.getSize());
                archivo.setArchivo(file.getBytes());//new String(file.getBytes(), StandardCharsets.UTF_8);

                archivos.add(archivo);
            }
        }
        
        bitacoraProyectoService.guardarBitacoraProyecto(proyectoDTO, archivos);

        redirectAttributes.addFlashAttribute("success", "Bitácora Proyecto: " + proyectoDTO.getBitacoraProyecto().getRevision() + " guardada exitosamente");

        return "redirect:" + RUTA_VISTA + "detalle/" + proyectoDTO.getBitacoraProyecto().getProyecto().getIdProyecto() + "?numeroPagina=1";
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPERVISOR", "ROLE_AUDITOR", "ROLE_REVISOR", "ROLE_DESARROLLO"})
    @GetMapping("/obtener")
    @ResponseBody
    public BitacoraProyecto obtenerBitacoraProyecto(Long idBitacoraProyecto, RedirectAttributes redirectAttributes) {
        BitacoraProyecto bitacoraProyecto = null;

        // Validar que exista la bitacora proyecto
        if (idBitacoraProyecto > 0) {
            bitacoraProyecto = new BitacoraProyecto();
            bitacoraProyecto.setIdBitacoraProyecto(idBitacoraProyecto);
            bitacoraProyecto = bitacoraProyectoService.buscarBitacoraProyecto(bitacoraProyecto);

            if (bitacoraProyecto == null) {
                redirectAttributes.addFlashAttribute("error", "El evento: " + idBitacoraProyecto + " no existe");
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "No se encontró el evento: " + idBitacoraProyecto);
        }

        return bitacoraProyecto;
    }

}
