package com.mhz.api.repository;

import com.mhz.api.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, String> {
    List<ItemPedido> findByPedidoId(String pedidoId);
}