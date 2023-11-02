package com.oghs.sgdsws.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.oghs.sgdsws.model.Estatus;
import com.oghs.sgdsws.model.entity.Hallazgo;
import com.oghs.sgdsws.repository.HallazgoRepository;
import com.oghs.sgdsws.util.Paginado;
import com.oghs.sgdsws.util.Paginando;

/**
 *
 * @author oghs
 */
@Service
public class HallazgoServiceImpl implements HallazgoService {

    private final HallazgoRepository hallazgoRepository;

    public HallazgoServiceImpl(HallazgoRepository hallazgoRepository) {
        this.hallazgoRepository = hallazgoRepository;
    }

    @Override
    public List<Hallazgo> obtenerHallazgos() {
        return hallazgoRepository.findAllByEstatusOrderByCodigoAsc(Estatus.ACTIVO);
    }

    @Override
    public Paginado<Hallazgo> obtenerHallazgosPaginado(int numeroPagina, int tamano) {
        PageRequest pageRequest = PageRequest.of(numeroPagina - 1, tamano, Sort.by(Sort.Direction.ASC, "idHallazgo"));
        Page<Hallazgo> hallazgoPage = hallazgoRepository.findAll(pageRequest);

        return new Paginado<>(hallazgoPage, Paginando.of(hallazgoPage.getTotalPages(), numeroPagina, tamano));
    }

    @Override
    public void guardarHallazgo(Hallazgo hallazgo) {
        hallazgo.setCodigo(hallazgo.getCodigo().toUpperCase());

        hallazgoRepository.save(hallazgo);
    }

    @Override
    public Hallazgo buscarHallazgo(Hallazgo hallazgo) {
        return hallazgoRepository.findById(hallazgo.getIdHallazgo()).orElse(null);
    }

    @Override
    public void eliminarHallazgo(Hallazgo hallazgo) {
        hallazgoRepository.deleteById(hallazgo.getIdHallazgo());
    }
    
}
