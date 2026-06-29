package com.mhz.api.service;

import com.mhz.api.dto.CategoriaDTO;
import com.mhz.api.entity.Categoria;
import com.mhz.api.repository.CategoriaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public CategoriaDTO obterPorId(String id) {
        Categoria categoria = categoriaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
        return convertToDTO(categoria);
    }

    public CategoriaDTO obterPorSlug(String slug) {
        Categoria categoria = categoriaRepository.findBySlug(slug)
            .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
        return convertToDTO(categoria);
    }

    public List<CategoriaDTO> obterTodas() {
        return categoriaRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public CategoriaDTO criar(CategoriaDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setNome(dto.getNome());
        categoria.setDescricao(dto.getDescricao());
        categoria.setImagemUrl(dto.getImagemUrl());
        categoria.setSlug(dto.getNome().toLowerCase().replace(" ", "-"));
        categoria.setAtivo(true);

        Categoria categoriaSalva = categoriaRepository.save(categoria);
        return convertToDTO(categoriaSalva);
    }

    public CategoriaDTO atualizar(String id, CategoriaDTO dto) {
        Categoria categoria = categoriaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        categoria.setNome(dto.getNome());
        categoria.setDescricao(dto.getDescricao());
        categoria.setImagemUrl(dto.getImagemUrl());
        categoria.setSlug(dto.getNome().toLowerCase().replace(" ", "-"));
        categoria.setAtivo(dto.getAtivo());

        Categoria categoriaAtualizada = categoriaRepository.save(categoria);
        return convertToDTO(categoriaAtualizada);
    }

    public void deletar(String id) {
        categoriaRepository.deleteById(id);
    }

    private CategoriaDTO convertToDTO(Categoria categoria) {
        return new CategoriaDTO(
            categoria.getId(),
            categoria.getNome(),
            categoria.getDescricao(),
            categoria.getImagemUrl(),
            categoria.getSlug(),
            categoria.getAtivo()
        );
    }
}