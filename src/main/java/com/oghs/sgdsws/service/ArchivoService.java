package com.oghs.sgdsws.service;

import com.oghs.sgdsws.model.entity.Archivo;

/**
 *
 * @author oghs
 */
public interface ArchivoService {
    
    public Archivo buscarArchivo(Archivo archivo);

    public void eliminarArchivo(Archivo archivo);
    
}
