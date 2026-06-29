package com.mhz.api.repository;

import com.mhz.api.entity.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, String> {
    Optional<Produto> findBySlug(String slug);
    
    Page<Produto> findByCategoriaId(String categoriaId, Pageable pageable);
    
    Page<Produto> findByMarcaId(String marcaId, Pageable pageable);
    
    Page<Produto> findByFornecedorId(String fornecedorId, Pageable pageable);
    
    @Query("SELECT p FROM Produto p WHERE p.disponivel = true AND p.ativo = true")
    Page<Produto> findAllDisponivel(Pageable pageable);
    
    @Query("SELECT p FROM Produto p WHERE p.disponivel = true AND p.ativo = true AND LOWER(p.nome) LIKE LOWER(CONCAT('%', :termo, '%'))")
    Page<Produto> searchByNome(@Param("termo") String termo, Pageable pageable);
}