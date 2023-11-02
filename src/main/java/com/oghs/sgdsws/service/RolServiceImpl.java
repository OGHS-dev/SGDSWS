package com.oghs.sgdsws.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.oghs.sgdsws.model.Estatus;
import com.oghs.sgdsws.model.entity.Rol;
import com.oghs.sgdsws.repository.RolRepository;
import com.oghs.sgdsws.util.Paginado;
import com.oghs.sgdsws.util.Paginando;

/**
 *
 * @author oghs
 */
@Service
public class RolServiceImpl implements RolService {

    private final RolRepository rolRepository;

    public RolServiceImpl(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @Override
    public List<Rol> obtenerRoles() {
        List<Rol> listaRoles = rolRepository.findAllByEstatusOrderByDescripcionAsc(Estatus.ACTIVO);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return listaRoles;
        } else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_SUPERVISOR"))) {
            listaRoles.removeIf(r -> r.getDescripcion().equals("ROLE_ADMIN"));
        } else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_AUDITOR"))) {
            listaRoles.removeIf(r -> r.getDescripcion().equals("ROLE_ADMIN"));
            listaRoles.removeIf(r -> r.getDescripcion().equals("ROLE_SUPERVISOR"));
        } else {
            listaRoles.clear();
        }

        return listaRoles;
    }

    @Override
    public Paginado<Rol> obtenerRolesPaginado(int numeroPagina, int tamano) {
        PageRequest pageRequest = PageRequest.of(numeroPagina - 1, tamano, Sort.by(Sort.Direction.ASC, "idRol"));
        Page<Rol> rolesPage = rolRepository.findAll(pageRequest);
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
