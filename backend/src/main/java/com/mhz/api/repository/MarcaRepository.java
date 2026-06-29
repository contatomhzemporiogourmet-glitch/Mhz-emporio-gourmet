package com.mhz.api.repository;

import com.mhz.api.entity.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, String> {
    Optional<Marca> findBySlug(String slug);
    Optional<Marca> findByNome(String nome);
}