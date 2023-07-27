package com.oghs.sgdsws.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.oghs.sgdsws.model.entity.Categoria;
import com.oghs.sgdsws.model.repository.CategoriaRepository;
import com.oghs.sgdsws.util.Paginado;
import com.oghs.sgdsws.util.Paginando;

/**
 *
 * @author oghs
 */
@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public List<Categoria> obtenerCategorias() {
        return (List<Categoria>) categoriaRepository.findAll();
    }

    @Override
    public Paginado<Categoria> obtenerCategoriasPaginado(int numeroPagina, int tamano) {
        PageRequest pageRequest = PageRequest.of(numeroPagina - 1, tamano, Sort.by(Sort.Direction.ASC, "idCategoria"));
        Page<Categoria> categoriasPage = (Page<Categoria>) categoriaRepository.findAll(pageRequest);
        
        return new Paginado<>(categoriasPage, Paginando.of(categoriasPage.getTotalPages(), numeroPagina, tamano));
    }

    @Override
    public void guardarCategoria(Categoria categoria) {
        categoria.setCodigo(categoria.getCodigo().toUpperCase());

        categoriaRepository.save(categoria);
    }

    @Override
    public Categoria buscarCategoria(Categoria categoria) {
        return categoriaRepository.findById(categoria.getIdCategoria()).orElse(null);
    }

    @Override
    public void eliminarCategoria(Categoria categoria) {
        categoriaRepository.deleteById(categoria.getIdCategoria());
    }
    
}
