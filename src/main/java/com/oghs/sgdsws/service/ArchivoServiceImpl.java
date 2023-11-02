package com.oghs.sgdsws.service;

import org.springframework.stereotype.Service;

import com.oghs.sgdsws.model.entity.Archivo;
import com.oghs.sgdsws.repository.ArchivoRepository;

/**
 *
 * @author oghs
 */
@Service
public class ArchivoServiceImpl implements ArchivoService {

    private final ArchivoRepository archivoRepository;

    public ArchivoServiceImpl(ArchivoRepository archivoRepository) {
        this.archivoRepository = archivoRepository;
    }

    @Override
    public Archivo buscarArchivo(Archivo archivo) {
        return archivoRepository.findById(archivo.getIdArchivo()).orElse(null);
    }

    @Override
    public void eliminarArchivo(Archivo archivo) {
        archivoRepository.deleteById(archivo.getIdArchivo());
    }

}
