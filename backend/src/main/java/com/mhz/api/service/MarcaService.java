package com.mhz.api.service;

import com.mhz.api.dto.MarcaDTO;
import com.mhz.api.entity.Marca;
import com.mhz.api.repository.MarcaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MarcaService {
    private final MarcaRepository marcaRepository;

    public MarcaService(MarcaRepository marcaRepository) {
        this.marcaRepository = marcaRepository;
    }

    public MarcaDTO obterPorId(String id) {
        Marca marca = marcaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Marca não encontrada"));
        return convertToDTO(marca);
    }

    public List<MarcaDTO> obterTodas() {
        return marcaRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public MarcaDTO criar(MarcaDTO dto) {
        Marca marca = new Marca();
        marca.setNome(dto.getNome());
        marca.setLogoUrl(dto.getLogoUrl());
        marca.setSlug(dto.getNome().toLowerCase().replace(" ", "-"));
        marca.setAtivo(true);

        Marca marcaSalva = marcaRepository.save(marca);
        return convertToDTO(marcaSalva);
    }

    public MarcaDTO atualizar(String id, MarcaDTO dto) {
        Marca marca = marcaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Marca não encontrada"));

        marca.setNome(dto.getNome());
        marca.setLogoUrl(dto.getLogoUrl());
        marca.setSlug(dto.getNome().toLowerCase().replace(" ", "-"));
        marca.setAtivo(dto.getAtivo());

        Marca marcaAtualizada = marcaRepository.save(marca);
        return convertToDTO(marcaAtualizada);
    }

    public void deletar(String id) {
        marcaRepository.deleteById(id);
    }

    private MarcaDTO convertToDTO(Marca marca) {
        return new MarcaDTO(
            marca.getId(),
            marca.getNome(),
            marca.getLogoUrl(),
            marca.getSlug(),
            marca.getAtivo()
        );
    }
}