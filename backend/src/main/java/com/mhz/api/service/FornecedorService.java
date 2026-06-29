package com.mhz.api.service;

import com.mhz.api.dto.FornecedorDTO;
import com.mhz.api.entity.Fornecedor;
import com.mhz.api.repository.FornecedorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FornecedorService {
    private final FornecedorRepository fornecedorRepository;

    public FornecedorService(FornecedorRepository fornecedorRepository) {
        this.fornecedorRepository = fornecedorRepository;
    }

    public FornecedorDTO obterPorId(String id) {
        Fornecedor fornecedor = fornecedorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Fornecedor não encontrado"));
        return convertToDTO(fornecedor);
    }

    public List<FornecedorDTO> obterTodos() {
        return fornecedorRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public FornecedorDTO criar(FornecedorDTO dto) {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome(dto.getNome());
        fornecedor.setCnpj(dto.getCnpj());
        fornecedor.setEmail(dto.getEmail());
        fornecedor.setTelefone(dto.getTelefone());
        fornecedor.setEndereco(dto.getEndereco());
        fornecedor.setCidade(dto.getCidade());
        fornecedor.setEstado(dto.getEstado());
        fornecedor.setCep(dto.getCep());
        fornecedor.setAtivo(true);

        Fornecedor fornecedorSalvo = fornecedorRepository.save(fornecedor);
        return convertToDTO(fornecedorSalvo);
    }

    public FornecedorDTO atualizar(String id, FornecedorDTO dto) {
        Fornecedor fornecedor = fornecedorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Fornecedor não encontrado"));

        fornecedor.setNome(dto.getNome());
        fornecedor.setEmail(dto.getEmail());
        fornecedor.setTelefone(dto.getTelefone());
        fornecedor.setEndereco(dto.getEndereco());
        fornecedor.setCidade(dto.getCidade());
        fornecedor.setEstado(dto.getEstado());
        fornecedor.setCep(dto.getCep());
        fornecedor.setAtivo(dto.getAtivo());

        Fornecedor fornecedorAtualizado = fornecedorRepository.save(fornecedor);
        return convertToDTO(fornecedorAtualizado);
    }

    public void deletar(String id) {
        fornecedorRepository.deleteById(id);
    }

    private FornecedorDTO convertToDTO(Fornecedor fornecedor) {
        return new FornecedorDTO(
            fornecedor.getId(),
            fornecedor.getNome(),
            fornecedor.getCnpj(),
            fornecedor.getEmail(),
            fornecedor.getTelefone(),
            fornecedor.getEndereco(),
            fornecedor.getCidade(),
            fornecedor.getEstado(),
            fornecedor.getCep(),
            fornecedor.getAtivo()
        );
    }
}