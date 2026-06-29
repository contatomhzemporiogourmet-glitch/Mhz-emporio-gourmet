package com.mhz.api.repository;

import com.mhz.api.entity.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, String> {
    Optional<Pedido> findByNumeroPedido(String numeroPedido);
    
    Page<Pedido> findByClienteId(String clienteId, Pageable pageable);
    
    Page<Pedido> findByStatus(String status, Pageable pageable);
}