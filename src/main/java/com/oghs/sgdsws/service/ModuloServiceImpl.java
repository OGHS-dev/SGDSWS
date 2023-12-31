package com.oghs.sgdsws.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.oghs.sgdsws.model.Estatus;
import com.oghs.sgdsws.model.entity.Modulo;
import com.oghs.sgdsws.repository.ModuloRepository;
import com.oghs.sgdsws.util.Paginado;
import com.oghs.sgdsws.util.Paginando;

/**
 *
 * @author oghs
 */
@Service
public class ModuloServiceImpl implements ModuloService {

    private final ModuloRepository moduloRepository;

    public ModuloServiceImpl(ModuloRepository moduloRepository) {
        this.moduloRepository = moduloRepository;
    }

    @Override
    public List<Modulo> obtenerModulos() {
        return moduloRepository.findAllByEstatusOrderByCodigoAsc(Estatus.ACTIVO);
    }

    @Override
    public Paginado<Modulo> obtenerModulosPaginado(int numeroPagina, int tamano) {
        PageRequest pageRequest = PageRequest.of(numeroPagina - 1, tamano, Sort.by(Sort.Direction.ASC, "idModulo"));
        Page<Modulo> modulosPage = moduloRepository.findAll(pageRequest);

        return new Paginado<>(modulosPage, Paginando.of(modulosPage.getTotalPages(), numeroPagina, tamano));
    }

    @Override
    public void guardarModulo(Modulo modulo) {
        modulo.setCodigo(modulo.getCodigo().toUpperCase());

        moduloRepository.save(modulo);
    }

    @Override
    public Modulo buscarModulo(Modulo modulo) {
        return moduloRepository.findById(modulo.getIdModulo()).orElse(null);
    }

    @Override
    public void eliminarModulo(Modulo modulo) {
        moduloRepository.deleteById(modulo.getIdModulo());
    }
    
}
