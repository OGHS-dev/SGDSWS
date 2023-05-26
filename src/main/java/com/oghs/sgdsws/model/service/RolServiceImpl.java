package com.oghs.sgdsws.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.oghs.sgdsws.model.entity.Rol;
import com.oghs.sgdsws.model.repository.RolRepository;
import com.oghs.sgdsws.util.Paginado;
import com.oghs.sgdsws.util.Paginando;

/**
 *
 * @author oghs
 */
@Service
public class RolServiceImpl implements RolService {

    @Autowired
    private RolRepository rolRepository;

    @Override
    public List<Rol> obtenerRoles() {
        return (List<Rol>) rolRepository.findAll();
    }

    @Override
    public Paginado<Rol> obtenerRolesPaginado(int numeroPagina, int tamano) {
        PageRequest pageRequest = PageRequest.of(numeroPagina - 1, tamano, Sort.by(Sort.Direction.ASC, "idRol"));
        Page<Rol> rolesPage = (Page<Rol>) rolRepository.findAll(pageRequest);
        return new Paginado<>(rolesPage, Paginando.of(rolesPage.getTotalPages(), numeroPagina, tamano));
    }

    @Override
    public void guardarRol(Rol rol) {
        rol.setCodigo(rol.getCodigo().toUpperCase());
        rol.setDescripcion("ROLE_".concat(rol.getDescripcion()).toUpperCase());
        
        rolRepository.save(rol);
    }

    @Override
    public Rol buscarRol(Rol rol) {
        return rolRepository.findById(rol.getIdRol()).orElse(null);
    }

    @Override
    public void eliminarRol(Rol rol) {
        rolRepository.deleteById(rol.getIdRol());
    }
    
}
