package com.oghs.sgdsws.application.port.in;

import java.util.List;

import com.oghs.sgdsws.dto.response.CatalogoResponse;

public interface ListCataloguesUseCase {
    List<CatalogoResponse> list(CatalogType type);
}
