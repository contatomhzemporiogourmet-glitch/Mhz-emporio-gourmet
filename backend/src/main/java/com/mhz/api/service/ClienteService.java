package com.mhz.api.service;

import com.mhz.api.dto.ClienteDTO;
import com.mhz.api.entity.Cliente;
import com.mhz.api.entity.Usuario;
import com.mhz.api.repository.ClienteRepository;
import com.mhz.api.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;

    public ClienteService(ClienteRepository clienteRepository, UsuarioRepository usuarioRepository) {
        this.clienteRepository = clienteRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public ClienteDTO obterPorId(String id) {
        Cliente cliente = clienteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        return convertToDTO(cliente);
    }

    public ClienteDTO obterPorUsuarioId(String usuarioId) {
        Cliente cliente = clienteRepository.findByUsuarioId(usuarioId)
            .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        return convertToDTO(cliente);
    }

    public ClienteDTO atualizar(String id, ClienteDTO dto) {
        Cliente cliente = clienteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        cliente.setTelefone(dto.getTelefone());
        cliente.setDataNascimento(dto.getDataNascimento());
        cliente.setGenero(dto.getGenero());
        cliente.setEndereco(dto.getEndereco());
        cliente.setNumero(dto.getNumero());
        cliente.setComplemento(dto.getComplemento());
        cliente.setBairro(dto.getBairro());
        cliente.setCidade(dto.getCidade());
        cliente.setEstado(dto.getEstado());
        cliente.setCep(dto.getCep());

        Cliente clienteAtualizado = clienteRepository.save(cliente);
        return convertToDTO(clienteAtualizado);
    }

    private ClienteDTO convertToDTO(Cliente cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.setId(cliente.getId());
        dto.setTelefone(cliente.getTelefone());
        dto.setCpf(cliente.getCpf());
        dto.setDataNascimento(cliente.getDataNascimento());
        dto.setGenero(cliente.getGenero());
        dto.setEndereco(cliente.getEndereco());
        dto.setNumero(cliente.getNumero());
        dto.setComplemento(cliente.getComplemento());
        dto.setBairro(cliente.getBairro());
        dto.setCidade(cliente.getCidade());
        dto.setEstado(cliente.getEstado());
        dto.setCep(cliente.getCep());
        dto.setPontosFidelidade(cliente.getPontosFidelidade());
        return dto;
    }
}