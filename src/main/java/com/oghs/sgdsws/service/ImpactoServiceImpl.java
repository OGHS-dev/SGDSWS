package com.oghs.sgdsws.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.oghs.sgdsws.model.entity.Impacto;
import com.oghs.sgdsws.repository.ImpactoRepository;
import com.oghs.sgdsws.util.Paginado;
import com.oghs.sgdsws.util.Paginando;

/**
 *
 * @author oghs
 */
@Service
public class ImpactoServiceImpl implements ImpactoService {

    @Autowired
    private ImpactoRepository impactoRepository;

    @Override
    public List<Impacto> obtenerImpactos() {
        return (List<Impacto>) impactoRepository.findAll();
    }

    @Override
    public Paginado<Impacto> obtenerImpactosPaginado(int numeroPagina, int tamano) {
        PageRequest pageRequest = PageRequest.of(numeroPagina - 1, tamano, Sort.by(Sort.Direction.ASC, "idImpacto"));
        Page<Impacto> impactosPage = (Page<Impacto>) impactoRepository.findAll(pageRequest);
        
        return new Paginado<>(impactosPage, Paginando.of(impactosPage.getTotalPages(), numeroPagina, tamano));
    }

    @Override
    public void guardarImpacto(Impacto impacto) {
        impacto.setCodigo(impacto.getCodigo().toUpperCase());

        impactoRepository.save(impacto);
    }

    @Override
    public Impacto buscarImpacto(Impacto impacto) {
        return impactoRepository.findById(impacto.getIdImpacto()).orElse(null);
    }

    @Override
    public void eliminarImpacto(Impacto impacto) {
        impactoRepository.deleteById(impacto.getIdImpacto());
    }
    
}
