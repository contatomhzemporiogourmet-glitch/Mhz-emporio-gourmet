package com.mhz.api.repository;

import com.mhz.api.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, String> {
    Optional<Categoria> findBySlug(String slug);
    Optional<Categoria> findByNome(String nome);
}