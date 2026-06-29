package com.mhz.api.repository;

import com.mhz.api.entity.Cupom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CupomRepository extends JpaRepository<Cupom, String> {
    Optional<Cupom> findByCodigo(String codigo);
}