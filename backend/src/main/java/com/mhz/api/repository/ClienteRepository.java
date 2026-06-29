package com.mhz.api.repository;

import com.mhz.api.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, String> {
    Optional<Cliente> findByUsuarioId(String usuarioId);
    Optional<Cliente> findByCpf(String cpf);
}