package com.oghs.sgdsws.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.oghs.sgdsws.application.port.in.CatalogType;
import com.oghs.sgdsws.application.port.out.CatalogQueryPort;
import com.oghs.sgdsws.dto.response.CatalogoResponse;

class CatalogueServiceTest {

    @Test
    void listsEntriesFromThePortForTheRequestedCatalogue() {
        CatalogQueryPort port = new CatalogQueryPort() {
            public CatalogType type() {
                return CatalogType.CATEGORIAS;
            }

            public List<CatalogoResponse> findActive() {
                return List.of(new CatalogoResponse(1L, "CAT-1", "Categoría"));
            }
        };

        var service = new CatalogueService(List.of(port));

        assertEquals(List.of(new CatalogoResponse(1L, "CAT-1", "Categoría")),
                service.list(CatalogType.CATEGORIAS));
    }
}
