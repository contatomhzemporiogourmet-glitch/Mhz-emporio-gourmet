package com.mhz.api.repository;

import com.mhz.api.entity.Compra;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompraRepository extends JpaRepository<Compra, String> {
    Optional<Compra> findByNumeroCompra(String numeroCompra);
    
    Page<Compra> findByFornecedorId(String fornecedorId, Pageable pageable);
    
    Page<Compra> findByStatus(String status, Pageable pageable);
}