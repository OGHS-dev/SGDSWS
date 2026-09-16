package com.oghs.sgdsws.application.service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oghs.sgdsws.application.port.in.CatalogType;
import com.oghs.sgdsws.application.port.in.ListCataloguesUseCase;
import com.oghs.sgdsws.application.port.out.CatalogQueryPort;
import com.oghs.sgdsws.dto.response.CatalogoResponse;

@Service
public class CatalogueService implements ListCataloguesUseCase {
    private final Map<CatalogType, CatalogQueryPort> catalogues;

    public CatalogueService(List<CatalogQueryPort> cataloguePorts) {
        this.catalogues = new EnumMap<>(CatalogType.class);
        cataloguePorts.forEach(port -> this.catalogues.put(port.type(), port));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CatalogoResponse> list(CatalogType type) {
        CatalogQueryPort port = catalogues.get(type);
        if (port == null) {
            throw new IllegalArgumentException("Catálogo no soportado: " + type);
        }
        return port.findActive();
    }
}
