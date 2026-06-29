package com.mhz.api.repository;

import com.mhz.api.entity.ImagemProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImagemProdutoRepository extends JpaRepository<ImagemProduto, String> {
    List<ImagemProduto> findByProdutoId(String produtoId);
}