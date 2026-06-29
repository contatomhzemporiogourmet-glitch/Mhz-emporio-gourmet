package com.mhz.api.repository;

import com.mhz.api.entity.Avaliacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, String> {
    Page<Avaliacao> findByProdutoId(String produtoId, Pageable pageable);
    
    List<Avaliacao> findByClienteId(String clienteId);
    
    List<Avaliacao> findByProdutoIdAndClienteId(String produtoId, String clienteId);
}