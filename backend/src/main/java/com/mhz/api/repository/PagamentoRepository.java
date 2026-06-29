package com.mhz.api.repository;

import com.mhz.api.entity.Pagamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, String> {
    List<Pagamento> findByPedidoId(String pedidoId);
    
    Page<Pagamento> findByStatus(String status, Pageable pageable);
}