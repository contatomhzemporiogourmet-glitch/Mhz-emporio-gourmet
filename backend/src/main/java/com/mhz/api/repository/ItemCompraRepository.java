package com.mhz.api.repository;

import com.mhz.api.entity.ItemCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemCompraRepository extends JpaRepository<ItemCompra, String> {
    List<ItemCompra> findByCompraId(String compraId);
}