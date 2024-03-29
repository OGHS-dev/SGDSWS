package com.oghs.sgdsws.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.data.repository.query.Param;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.oghs.sgdsws.model.DatosGraficasDTO;
import com.oghs.sgdsws.model.ProyectoDTO;
import com.oghs.sgdsws.model.entity.Archivo;
import com.oghs.sgdsws.model.entity.BitacoraProyecto;
import com.oghs.sgdsws.model.entity.Categoria;
import com.oghs.sgdsws.model.entity.EstadoBitacoraProyecto;
import com.oghs.sgdsws.model.entity.EstadoProyecto;
import com.oghs.sgdsws.model.entity.Hallazgo;
import com.oghs.sgdsws.model.entity.Impacto;
import com.oghs.sgdsws.model.entity.Incidente;
import com.oghs.sgdsws.model.entity.Modulo;
import com.oghs.sgdsws.model.entity.NivelRiesgo;
import com.oghs.sgdsws.model.entity.Prioridad;
import com.oghs.sgdsws.model.entity.Proyecto;
import com.oghs.sgdsws.service.ArchivoService;
import com.oghs.sgdsws.service.BitacoraProyectoService;
import com.oghs.sgdsws.service.CategoriaService;
import com.oghs.sgdsws.service.EstadoBitacoraProyectoService;
import com.oghs.sgdsws.service.EstadoProyectoService;
import com.oghs.sgdsws.service.HallazgoService;
import com.oghs.sgdsws.service.ImpactoService;
import com.oghs.sgdsws.service.IncidenteService;
import com.oghs.sgdsws.service.ModuloService;
import com.oghs.sgdsws.service.NivelRiesgoService;
import com.oghs.sgdsws.service.PrioridadService;
import com.oghs.sgdsws.service.ProyectoService;
import com.oghs.sgdsws.service.UsuarioService;
import com.oghs.sgdsws.util.GeneradorReportes;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

/**
 * BitacoraProyectoController es la clase controlador para ejecutar las
 * operaciones CRUD de los eventos de un proyecto.
 * 
 * @author oghs
 * @version 1.0
 */
@Controller
@RequestMapping("/views/proyectos/detalle")
public class BitacoraProyectoController {

    private static final String RUTA_VISTA = "/views/proyectos/";

    Logger logger = Logger.getLogger(getClass().getName());

    private final ProyectoService proyectoService;

    private final BitacoraProyectoService bitacoraProyectoService;

    private final UsuarioService usuarioService;

    private final ModuloService moduloService;

    private final HallazgoService hallazgoService;

    private final IncidenteService incidenteService;

    private final CategoriaService categoriaService;

    private final PrioridadService prioridadService;

    private final ImpactoService impactoService;

    private final NivelRiesgoService nivelRiesgoService;

    private final EstadoProyectoService estadoProyectoService;

    private final EstadoBitacoraProyectoService estadoBitacoraProyectoService;

    private final ArchivoService archivoService;

    public BitacoraProyectoController(ProyectoService proyectoService, BitacoraProyectoService bitacoraProyectoService, UsuarioService usuarioService, ModuloService moduloService, HallazgoService hallazgoService, IncidenteService incidenteService, CategoriaService categoriaService, PrioridadService prioridadService, ImpactoService impactoService, NivelRiesgoService nivelRiesgoService, EstadoProyectoService estadoProyectoService, EstadoBitacoraProyectoService estadoBitacoraProyectoService, ArchivoService archivoService) {
        this.proyectoService = proyectoService;
        this.bitacoraProyectoService = bitacoraProyectoService;
        this.usuarioService = usuarioService;
        this.moduloService = moduloService;
        this.hallazgoService = hallazgoService;
        this.incidenteService = incidenteService;
        this.categoriaService = categoriaService;
        this.prioridadService = prioridadService;
        this.impactoService = impactoService;
        this.nivelRiesgoService = nivelRiesgoService;
        this.estadoProyectoService = estadoProyectoService;
        this.estadoBitacoraProyectoService = estadoBitacoraProyectoService;
        this.archivoService = archivoService;
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPERVISOR", "ROLE_AUDITOR", "ROLE_REVISOR", "ROLE_DESARROLLO"})
    @GetMapping("/{idProyecto}")
    public String detalleProyecto(@PathVariable("idProyecto") Long idProyecto, @RequestParam(value = "numeroPagina", required = false, defaultValue = "1") int numeroPagina, @RequestParam(value = "tamano", required = false, defaultValue = "5") int tamano, Model model, RedirectAttributes redirectAttributes) {
        // Validar que exista el proyecto
        Proyecto proyecto = this.validarProyecto(idProyecto);

        if (Objects.isNull(proyecto)) {
            redirectAttributes.addFlashAttribute("error", String.format("El proyecto: %d no existe", idProyecto));
            
            return "redirect:" + RUTA_VISTA;
        } else {
            BitacoraProyecto bitacoraProyecto = new BitacoraProyecto();
            bitacoraProyecto.setProyecto(proyecto);

            ProyectoDTO proyectoDTO = new ProyectoDTO();
            proyectoDTO.setProyecto(proyecto);
            proyectoDTO.setBitacoraProyecto(bitacoraProyecto);
            proyectoDTO.setListaUsuariosProyecto(usuarioService.obtenerUsuariosPorProyecto(proyecto));
            proyectoDTO.setListaBitacoraProyectoPaginado(bitacoraProyectoService.obtenerBitacoraProyectoPorProyectoPaginado(proyecto, numeroPagina, tamano));

            model.addAttribute("titulo", "Detalle de Proyecto");
            model.addAttribute("proyectoDTO", proyectoDTO);
            model.addAttribute("listaModulos", moduloService.obtenerModulos());
            model.addAttribute("listaHallazgos", hallazgoService.obtenerHallazgos());
            model.addAttribute("listaIncidentes", incidenteService.obtenerIncidentes());
            model.addAttribute("listaCategorias", categoriaService.obtenerCategorias());
            model.addAttribute("listaPrioridades", prioridadService.obtenerPrioridades());
            model.addAttribute("listaImpactos", impactoService.obtenerImpactos());
            model.addAttribute("listaNivelesRiesgo", nivelRiesgoService.obtenerNivelesRiesgo());
            model.addAttribute("listaEstadosProyecto", estadoProyectoService.obtenerEstadosProyecto());
            model.addAttribute("listaEstadosBitacoraProyecto", estadoBitacoraProyectoService.obtenerEstadosBitacoraProyecto());
            model.addAttribute("datosGraficas", this.datosGraficas(proyecto));

            return RUTA_VISTA + "detalleProyecto";
        }
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPERVISOR", "ROLE_AUDITOR"})
    @PostMapping("/actualizar/{idProyecto}")
    public String actualizarEstadoProyecto(@PathVariable("idProyecto") Long idProyecto, Model model, RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) {
        // Validar que exista el proyecto
        Proyecto proyecto = this.validarProyecto(idProyecto);

        if (Objects.isNull(proyecto)) {
            redirectAttributes.addFlashAttribute("error", String.format("El proyecto: %d no existe", idProyecto));
            
            return "redirect:" + RUTA_VISTA;
        } else {
            List<BitacoraProyecto> eventosProyecto = proyecto.getBitacoraProyecto().stream()
                    .filter(bp -> !bp.getEstadoBitacoraProyecto().getCodigo().equals("ECRE")
                            && !bp.getEstadoBitacoraProyecto().getCodigo().equals("EMOD")
                            && !bp.getEstadoBitacoraProyecto().getCodigo().equals("ECOM")
                            && !bp.getEstadoBitacoraProyecto().getCodigo().equals("ECER"))
                    .collect(Collectors.toList());
            if (eventosProyecto.isEmpty()) {
                EstadoProyecto estadoProyecto = new EstadoProyecto();
                estadoProyecto.setIdEstadoProyecto(Long.parseLong(httpServletRequest.getParameter("proyecto.estadoProyecto.idEstadoProyecto")));

                proyecto.setEstadoProyecto(estadoProyecto);
                
                ProyectoDTO proyectoDTO = new ProyectoDTO();
                proyectoDTO.setProyecto(proyecto);
                proyectoService.guardarProyecto(proyectoDTO);
                
                redirectAttributes.addFlashAttribute("success", String.format("Proyecto: %s actualizado exitosamente", proyecto.getNombre()));
            } else {
                String eventosPendientes = eventosProyecto.stream().map(ep -> ep.getDescripcion() + ", ").collect(Collectors.joining());
                redirectAttributes.addFlashAttribute("warning", String.format("El estado del proyecto: %s no se puede actualizar al siguiente estado debido a que no se han completado o cerrado los siguientes eventos: %s", proyecto.getNombre(), eventosPendientes));
            }

            return "redirect:" + RUTA_VISTA + "detalle/" + idProyecto + "?numeroPagina=1";
        }
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPERVISOR", "ROLE_AUDITOR", "ROLE_REVISOR", "ROLE_DESARROLLO"})
    @GetMapping("/descargarArchivo")
    public void descargarArchivo(@Param("idArchivo") Long idArchivo, HttpServletResponse httpServletResponse) throws IOException {
        Archivo archivo = new Archivo();
        archivo.setIdArchivo(idArchivo);
        archivo = archivoService.buscarArchivo(archivo);

        if (!Objects.isNull(archivo)) {
            httpServletResponse.setContentType("application/octet-stream");
            httpServletResponse.setHeader("Content-Disposition", "attachment; filename = " + archivo.getNombreArchivo());
            ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
            servletOutputStream.write(archivo.getArchivo());
            servletOutputStream.close();
        } else {
            logger.info("El archivo no existe");
        }
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPERVISOR", "ROLE_AUDITOR", "ROLE_REVISOR", "ROLE_DESARROLLO"})
    @GetMapping("/eliminarArchivo")
    public String eliminarArchivo(@Param("idProyecto") Long idProyecto, @Param("idArchivo") Long idArchivo, HttpServletResponse httpServletResponse, RedirectAttributes redirectAttributes) {
        Archivo archivo = new Archivo();
        archivo.setIdArchivo(idArchivo);
        archivo = archivoService.buscarArchivo(archivo);

        if (!Objects.isNull(archivo)) {
            archivoService.eliminarArchivo(archivo);

            redirectAttributes.addFlashAttribute("success", "Archivo: " + archivo.getNombreArchivo() + " eliminado exitosamente");
        } else {
            redirectAttributes.addFlashAttribute("error", "El archivo con ID: " + idArchivo + " no existe");
        }

        return "redirect:" + RUTA_VISTA + "detalle/" + idProyecto + "?numeroPagina=1";
    }
    
    /**
     * Retorna la vista (HTML) de detalle del proyecto al guardar un nuevo evento de un proyecto.
     *
     * @param proyectoDTO el objeto DTO del proyecto el cual contiene todos los atributos a guardar
     * @param bindingResult el objeto que contiene si hay errores o no en el formulario
     * @param model el objeto para mandar atributos y valores a la vista
     * @param redirectAttributes el objeto para mandar atributos flash
     * @param multipartFile la lista de objetos que recibe archivos cargados
     * @return la cadena con la ruta de la vista (HTML) a retornar
     * @throws IOException si ocurre una excepción en la entrada o salida de datos
     */
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
            model.addAttribute("listaModulos", moduloService.obtenerModulos());
            model.addAttribute("listaHallazgos", hallazgoService.obtenerHallazgos());
            model.addAttribute("listaIncidentes", incidenteService.obtenerIncidentes());
            model.addAttribute("listaCategorias", categoriaService.obtenerCategorias());
            model.addAttribute("listaPrioridades", prioridadService.obtenerPrioridades());
            model.addAttribute("listaImpactos", impactoService.obtenerImpactos());
            model.addAttribute("listaNivelesRiesgo", nivelRiesgoService.obtenerNivelesRiesgo());
            model.addAttribute("listaEstadosProyecto", estadoProyectoService.obtenerEstadosProyecto());
            model.addAttribute("listaEstadosBitacoraProyecto", estadoBitacoraProyectoService.obtenerEstadosBitacoraProyecto());
            model.addAttribute("datosGraficas", this.datosGraficas(proyecto));

            String errores = "Error en los datos proporcionados:\n\n" + bindingResult.getFieldErrors().stream().map(error -> error.getDefaultMessage() + "\n").collect(Collectors.joining());
            model.addAttribute("warning", errores);

            return RUTA_VISTA + "detalleProyecto";
        }
        
        List<Archivo> archivos = new ArrayList<>();
        if (multipartFile.length > 0) {
            for (MultipartFile file : multipartFile) {
                if (file.getSize() > 0) {
                    Archivo archivo = new Archivo();
                    archivo.setNombreArchivo(file.getOriginalFilename());
                    // file.getContentType();
                    archivo.setTamanoArchivo(file.getSize());
                    archivo.setArchivo(file.getBytes());//new String(file.getBytes(), StandardCharsets.UTF_8);

                    archivos.add(archivo);
                }
            }
        }

        // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // if (authentication.getName().equals("ADMIN") || authentication.getName().equals(proyectoDTO.getBitacoraProyecto().getUsuarioReporte()) || authentication.getName().equals(proyectoDTO.getBitacoraProyecto().getUsuarioAsignado())) {
            bitacoraProyectoService.guardarBitacoraProyecto(proyectoDTO, archivos);

            redirectAttributes.addFlashAttribute("success", "Bitácora Proyecto: " + proyectoDTO.getBitacoraProyecto().getRevision() + " guardada exitosamente");
        // } else {
            // redirectAttributes.addFlashAttribute("warning", "No cuenta con permisos para actualizar el evento");
        // }

        return "redirect:" + RUTA_VISTA + "detalle/" + proyectoDTO.getBitacoraProyecto().getProyecto().getIdProyecto() + "?numeroPagina=1";
    }

    /**
     * Retorna el evento a buscar por idBitacoraProyecto para ver/editar el detalle del evento.
     *
     * @param idBitacoraProyecto el id del evento a buscar
     * @param redirectAttributes el objeto para mandar atributos flash
     * @return el objeto de bitacoraProyecto (evento)
     */
    @Secured({"ROLE_ADMIN", "ROLE_SUPERVISOR", "ROLE_AUDITOR", "ROLE_REVISOR", "ROLE_DESARROLLO"})
    @GetMapping("/obtener")
    @ResponseBody
    public BitacoraProyecto obtenerBitacoraProyecto(Long idBitacoraProyecto, RedirectAttributes redirectAttributes) {
        // Validar que exista la bitácora de proyecto
        BitacoraProyecto bitacoraProyecto = this.validarBitacoraProyecto(idBitacoraProyecto);

        if (Objects.isNull(bitacoraProyecto)) {
            redirectAttributes.addFlashAttribute("error", String.format("El evento: %d no existe", idBitacoraProyecto));
        }

        return bitacoraProyecto;
    }

    /**
     * Genera el reporte de Excel de los eventos asociados a un proyecto
     *
     * @param idBitacoraProyecto el id del evento a buscar
     * @param redirectAttributes el objeto para mandar atributos flash
     * @return el archivo Excel de reporte de proyecto
     */
    @Secured({"ROLE_ADMIN", "ROLE_SUPERVISOR", "ROLE_AUDITOR", "ROLE_REVISOR"})
    @GetMapping("/generarReporte")
    public void generarReporteExcel(Long idProyecto, RedirectAttributes redirectAttributes, HttpServletResponse httpServletResponse) throws IOException {
        // Validar que exista el proyecto
        Proyecto proyecto = this.validarProyecto(idProyecto);

        if (Objects.isNull(proyecto)) {
            redirectAttributes.addFlashAttribute("error", String.format("El proyecto: %d no existe", idProyecto));
        } else {
            httpServletResponse.setContentType("application/octet-stream");
            httpServletResponse.setHeader("Content-Disposition", "attachment; filename = " + new Date() + ".xlsx");

            GeneradorReportes generadorReportes = new GeneradorReportes(bitacoraProyectoService.obtenerBitacoraProyectoPorProyecto(proyecto));
            generadorReportes.generarArchivoExcel(httpServletResponse);
        }
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

    /**
     * Retorna un objeto BitacoraProyecto (evento) si éste existe y se encontró.
     *
     * @param idBitacoraProyecto el id de la bitácora de proyecto (evento) a buscar
     * @return bitacoraProyecto el objeto de la bitácora de proyecto (evento) encontrado
     */
    private BitacoraProyecto validarBitacoraProyecto(Long idBitacoraProyecto) {
        BitacoraProyecto bitacoraProyecto = null;

        if (idBitacoraProyecto > 0) {
            bitacoraProyecto = new BitacoraProyecto();
            bitacoraProyecto.setIdBitacoraProyecto(idBitacoraProyecto);
            bitacoraProyecto = bitacoraProyectoService.buscarBitacoraProyecto(bitacoraProyecto);

            // No se encontró la bitácora de proyecto
            if (Objects.isNull(bitacoraProyecto)) {
                return null;
            }
        }

        return bitacoraProyecto;
    }

    /**
     * Retorna un objeto DatosGraficasDTO con el contenido de estadísticas resultado del conteo
     * de cada estatus el cual tiene asignado cada proyecto.
     *
     * @param proyecto el proyecto del cual se generarán las gráficas
     * @return datosGraficasDTO el objeto con los datos de las gráficas
     */
    private DatosGraficasDTO datosGraficas(Proyecto proyecto) {
        DatosGraficasDTO datosGraficasDTO = new DatosGraficasDTO();

        Map<Modulo, Long> mapaModulos = new LinkedHashMap<>();
        moduloService.obtenerModulos().forEach(modulo -> mapaModulos.put(modulo, 0L));

        Map<Hallazgo, Long> mapaHallazgos = new LinkedHashMap<>();
        hallazgoService.obtenerHallazgos().forEach(hallazgo -> mapaHallazgos.put(hallazgo, 0L));

        Map<Incidente, Long> mapaIncidentes = new LinkedHashMap<>();
        incidenteService.obtenerIncidentes().forEach(incidente -> mapaIncidentes.put(incidente, 0L));

        Map<Categoria, Long> mapaCategorias = new LinkedHashMap<>();
        categoriaService.obtenerCategorias().forEach(categoria -> mapaCategorias.put(categoria, 0L));

        Map<Prioridad, Long> mapaPrioridades = new LinkedHashMap<>();
        prioridadService.obtenerPrioridades().forEach(prioridad -> mapaPrioridades.put(prioridad, 0L));

        Map<Impacto, Long> mapaImpactos = new LinkedHashMap<>();
        impactoService.obtenerImpactos().forEach(impacto -> mapaImpactos.put(impacto, 0L));

        Map<NivelRiesgo, Long> mapaNivelesRiesgo = new LinkedHashMap<>();
        nivelRiesgoService.obtenerNivelesRiesgo().forEach(nivelRiesgo -> mapaNivelesRiesgo.put(nivelRiesgo, 0L));

        Map<EstadoBitacoraProyecto, Long> mapaEstadosBitacoraProyecto = new LinkedHashMap<>();
        estadoBitacoraProyectoService.obtenerEstadosBitacoraProyecto().forEach(estadoBitacoraProyecto -> mapaEstadosBitacoraProyecto.put(estadoBitacoraProyecto, 0L));

        bitacoraProyectoService.obtenerBitacoraProyectoPorProyecto(proyecto).forEach(bitacoraProyecto -> {
            if (!Objects.isNull(bitacoraProyecto.getModulo())) {
                Long contModulos = mapaModulos.get(bitacoraProyecto.getModulo());
                mapaModulos.replace(bitacoraProyecto.getModulo(), !Objects.isNull(contModulos) ? ++contModulos : 0);
            }
            if (!Objects.isNull(bitacoraProyecto.getHallazgo())) {
                Long contHallazgos = mapaHallazgos.get(bitacoraProyecto.getHallazgo());
                mapaHallazgos.replace(bitacoraProyecto.getHallazgo(), !Objects.isNull(contHallazgos) ? ++contHallazgos : 0);
            }
            if (!Objects.isNull(bitacoraProyecto.getIncidente())) {
                Long contIncidentes = mapaIncidentes.get(bitacoraProyecto.getIncidente());
                mapaIncidentes.replace(bitacoraProyecto.getIncidente(), !Objects.isNull(contIncidentes) ? ++contIncidentes : 0);
            }
            if (!Objects.isNull(bitacoraProyecto.getCategoria())) {
                Long contCategorias = mapaCategorias.get(bitacoraProyecto.getCategoria());
                mapaCategorias.replace(bitacoraProyecto.getCategoria(), !Objects.isNull(contCategorias) ? ++contCategorias : 0);
            }
            if (!Objects.isNull(bitacoraProyecto.getPrioridad())) {
                Long contPrioridades = mapaPrioridades.get(bitacoraProyecto.getPrioridad());
                mapaPrioridades.replace(bitacoraProyecto.getPrioridad(), !Objects.isNull(contPrioridades) ? ++contPrioridades : 0);
            }
            if (!Objects.isNull(bitacoraProyecto.getImpacto())) {
                Long contImpactos = mapaImpactos.get(bitacoraProyecto.getImpacto());
                mapaImpactos.replace(bitacoraProyecto.getImpacto(), !Objects.isNull(contImpactos) ? ++contImpactos : 0);
            }
            if (!Objects.isNull(bitacoraProyecto.getNivelRiesgo())) {
                Long contNivelesRiesgo = mapaNivelesRiesgo.get(bitacoraProyecto.getNivelRiesgo());
                mapaNivelesRiesgo.replace(bitacoraProyecto.getNivelRiesgo(), !Objects.isNull(contNivelesRiesgo) ? ++contNivelesRiesgo : 0);
            }
            if (!Objects.isNull(bitacoraProyecto.getEstadoBitacoraProyecto())) {
                Long contEstadosBitacoraProyecto = mapaEstadosBitacoraProyecto.get(bitacoraProyecto.getEstadoBitacoraProyecto());
                mapaEstadosBitacoraProyecto.replace(bitacoraProyecto.getEstadoBitacoraProyecto(), !Objects.isNull(contEstadosBitacoraProyecto) ? ++contEstadosBitacoraProyecto : 0);
            }
        });

        List<Object> listaModulos = new ArrayList<>();
        List<Object> listaHallazgos = new ArrayList<>();
        List<Object> listaIncidentes = new ArrayList<>();
        List<Object> listaCategorias = new ArrayList<>();
        List<Object> listaPrioridades = new ArrayList<>();
        List<Object> listaImpactos = new ArrayList<>();
        List<Object> listaNivelesRiesgo = new ArrayList<>();
        List<Object> listaEstadosBitacoraProyecto = new ArrayList<>();

        mapaModulos.keySet().forEach(modulo -> listaModulos.add(List.of(modulo.getDescripcion(), mapaModulos.get(modulo))));
        mapaHallazgos.keySet().forEach(hallazgo -> listaHallazgos.add(List.of(hallazgo.getDescripcion(), mapaHallazgos.get(hallazgo))));
        mapaIncidentes.keySet().forEach(incidente -> listaIncidentes.add(List.of(incidente.getDescripcion(), mapaIncidentes.get(incidente))));
        mapaCategorias.keySet().forEach(categoria -> listaCategorias.add(List.of(categoria.getDescripcion(), mapaCategorias.get(categoria))));
        mapaPrioridades.keySet().forEach(prioridad -> listaPrioridades.add(List.of(prioridad.getDescripcion(), mapaPrioridades.get(prioridad))));
        mapaImpactos.keySet().forEach(impacto -> listaImpactos.add(List.of(impacto.getDescripcion(), mapaImpactos.get(impacto))));
        mapaNivelesRiesgo.keySet().forEach(nivelRiesgo -> listaNivelesRiesgo.add(List.of(nivelRiesgo.getDescripcion(), mapaNivelesRiesgo.get(nivelRiesgo))));
        mapaEstadosBitacoraProyecto.keySet().forEach(estadoBitacoraProyecto -> listaEstadosBitacoraProyecto.add(List.of(estadoBitacoraProyecto.getDescripcion(), mapaEstadosBitacoraProyecto.get(estadoBitacoraProyecto))));

        datosGraficasDTO.setListaModulos(listaModulos);
        datosGraficasDTO.setListaHallazgos(listaHallazgos);
        datosGraficasDTO.setListaIncidentes(listaIncidentes);
        datosGraficasDTO.setListaCategorias(listaCategorias);
        datosGraficasDTO.setListaPrioridades(listaPrioridades);
        datosGraficasDTO.setListaImpactos(listaImpactos);
        datosGraficasDTO.setListaNivelesRiesgo(listaNivelesRiesgo);
        datosGraficasDTO.setListaEstadosBitacoraProyecto(listaEstadosBitacoraProyecto);

        return datosGraficasDTO;
    }
    
}
