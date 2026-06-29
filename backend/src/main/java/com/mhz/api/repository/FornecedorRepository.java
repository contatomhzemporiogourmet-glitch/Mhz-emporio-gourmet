package com.mhz.api.repository;

import com.mhz.api.entity.Categoria;
import com.mhz.api.entity.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, String> {
    Optional<Fornecedor> findByCnpj(String cnpj);
    Optional<Fornecedor> findByNome(String nome);
}