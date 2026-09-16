package com.oghs.sgdsws.application.port.out;

import java.util.List;

import com.oghs.sgdsws.application.port.in.CatalogType;
import com.oghs.sgdsws.dto.response.CatalogoResponse;

public interface CatalogQueryPort {
    CatalogType type();
    List<CatalogoResponse> findActive();
}
