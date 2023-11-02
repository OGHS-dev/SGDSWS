package com.oghs.sgdsws.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.oghs.sgdsws.model.Estatus;
import com.oghs.sgdsws.model.entity.Categoria;
import com.oghs.sgdsws.repository.CategoriaRepository;
import com.oghs.sgdsws.util.Paginado;
import com.oghs.sgdsws.util.Paginando;

/**
 *
 * @author oghs
 */
@Service
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaServiceImpl(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public List<Categoria> obtenerCategorias() {
        return categoriaRepository.findAllByEstatusOrderByCodigoAsc(Estatus.ACTIVO);
    }

    @Override
    public Paginado<Categoria> obtenerCategoriasPaginado(int numeroPagina, int tamano) {
        PageRequest pageRequest = PageRequest.of(numeroPagina - 1, tamano, Sort.by(Sort.Direction.ASC, "idCategoria"));
        Page<Categoria> categoriasPage = categoriaRepository.findAll(pageRequest);
        
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
