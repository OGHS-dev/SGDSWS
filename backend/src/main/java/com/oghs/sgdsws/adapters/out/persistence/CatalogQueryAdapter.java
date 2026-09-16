package com.oghs.sgdsws.adapters.out.persistence;

import java.util.List;
import java.util.function.Supplier;

import com.oghs.sgdsws.application.port.in.CatalogType;
import com.oghs.sgdsws.application.port.out.CatalogQueryPort;
import com.oghs.sgdsws.dto.response.CatalogoResponse;

public final class CatalogQueryAdapter implements CatalogQueryPort {
    private final CatalogType type;
    private final Supplier<List<CatalogoResponse>> query;

    public CatalogQueryAdapter(CatalogType type, Supplier<List<CatalogoResponse>> query) {
        this.type = type;
        this.query = query;
    }

    @Override
    public CatalogType type() {
        return type;
    }

    @Override
    public List<CatalogoResponse> findActive() {
        return query.get();
    }
}
