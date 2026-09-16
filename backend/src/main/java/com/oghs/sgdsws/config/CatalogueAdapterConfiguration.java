package com.oghs.sgdsws.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.oghs.sgdsws.adapters.out.persistence.CatalogQueryAdapter;
import com.oghs.sgdsws.application.port.in.CatalogType;
import com.oghs.sgdsws.application.port.out.CatalogQueryPort;
import com.oghs.sgdsws.model.Estatus;
import com.oghs.sgdsws.repository.CategoriaRepository;
import com.oghs.sgdsws.repository.EstadoBitacoraProyectoRepository;
import com.oghs.sgdsws.repository.EstadoProyectoRepository;
import com.oghs.sgdsws.repository.HallazgoRepository;
import com.oghs.sgdsws.repository.ImpactoRepository;
import com.oghs.sgdsws.repository.IncidenteRepository;
import com.oghs.sgdsws.repository.ModuloRepository;
import com.oghs.sgdsws.repository.NivelRiesgoRepository;
import com.oghs.sgdsws.repository.PrioridadRepository;

@Configuration
public class CatalogueAdapterConfiguration {
    @Bean
    CatalogQueryPort projectStates(EstadoProyectoRepository repository) {
        return new CatalogQueryAdapter(CatalogType.ESTADOS_PROYECTO,
                () -> repository.findAllByEstatusOrderByDescripcionAsc(Estatus.ACTIVO).stream()
                        .map(item -> new com.oghs.sgdsws.dto.response.CatalogoResponse(
                                item.getIdEstadoProyecto(), item.getCodigo(), item.getDescripcion())).toList());
    }

    @Bean
    CatalogQueryPort categories(CategoriaRepository repository) {
        return new CatalogQueryAdapter(CatalogType.CATEGORIAS,
                () -> repository.findAllByEstatusOrderByDescripcionAsc(Estatus.ACTIVO).stream()
                        .map(item -> new com.oghs.sgdsws.dto.response.CatalogoResponse(
                                item.getIdCategoria(), item.getCodigo(), item.getDescripcion())).toList());
    }

    @Bean
    CatalogQueryPort priorities(PrioridadRepository repository) {
        return new CatalogQueryAdapter(CatalogType.PRIORIDADES,
                () -> repository.findAllByEstatusOrderByDescripcionAsc(Estatus.ACTIVO).stream()
                        .map(item -> new com.oghs.sgdsws.dto.response.CatalogoResponse(
                                item.getIdPrioridad(), item.getCodigo(), item.getDescripcion())).toList());
    }

    @Bean
    CatalogQueryPort modules(ModuloRepository repository) {
        return new CatalogQueryAdapter(CatalogType.MODULOS,
                () -> repository.findAllByEstatusOrderByDescripcionAsc(Estatus.ACTIVO).stream()
                        .map(item -> new com.oghs.sgdsws.dto.response.CatalogoResponse(
                                item.getIdModulo(), item.getCodigo(), item.getDescripcion())).toList());
    }

    @Bean
    CatalogQueryPort findings(HallazgoRepository repository) {
        return new CatalogQueryAdapter(CatalogType.HALLAZGOS,
                () -> repository.findAllByEstatusOrderByDescripcionAsc(Estatus.ACTIVO).stream()
                        .map(item -> new com.oghs.sgdsws.dto.response.CatalogoResponse(
                                item.getIdHallazgo(), item.getCodigo(), item.getDescripcion())).toList());
    }

    @Bean
    CatalogQueryPort incidents(IncidenteRepository repository) {
        return new CatalogQueryAdapter(CatalogType.INCIDENTES,
                () -> repository.findAllByEstatusOrderByDescripcionAsc(Estatus.ACTIVO).stream()
                        .map(item -> new com.oghs.sgdsws.dto.response.CatalogoResponse(
                                item.getIdIncidente(), item.getCodigo(), item.getDescripcion())).toList());
    }

    @Bean
    CatalogQueryPort riskLevels(NivelRiesgoRepository repository) {
        return new CatalogQueryAdapter(CatalogType.NIVELES_RIESGO,
                () -> repository.findAllByEstatusOrderByDescripcionAsc(Estatus.ACTIVO).stream()
                        .map(item -> new com.oghs.sgdsws.dto.response.CatalogoResponse(
                                item.getIdNivelRiesgo(), item.getCodigo(), item.getDescripcion())).toList());
    }

    @Bean
    CatalogQueryPort impacts(ImpactoRepository repository) {
        return new CatalogQueryAdapter(CatalogType.IMPACTOS,
                () -> repository.findAllByEstatusOrderByDescripcionAsc(Estatus.ACTIVO).stream()
                        .map(item -> new com.oghs.sgdsws.dto.response.CatalogoResponse(
                                item.getIdImpacto(), item.getCodigo(), item.getDescripcion())).toList());
    }

    @Bean
    CatalogQueryPort projectLogStates(EstadoBitacoraProyectoRepository repository) {
        return new CatalogQueryAdapter(CatalogType.ESTADOS_BITACORA_PROYECTO,
                () -> repository.findAllByEstatusOrderByDescripcionAsc(Estatus.ACTIVO).stream()
                        .map(item -> new com.oghs.sgdsws.dto.response.CatalogoResponse(
                                item.getIdEstadoBitacoraProyecto(), item.getCodigo(), item.getDescripcion())).toList());
    }
}
