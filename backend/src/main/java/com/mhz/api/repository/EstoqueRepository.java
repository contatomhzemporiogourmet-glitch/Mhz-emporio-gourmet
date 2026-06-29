package com.mhz.api.repository;

import com.mhz.api.entity.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstoqueRepository extends JpaRepository<Estoque, String> {
    Optional<Estoque> findByProdutoId(String produtoId);
}