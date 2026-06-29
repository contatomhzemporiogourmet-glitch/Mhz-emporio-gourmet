package com.mhz.api.dto;

import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemPedidoCreateDTO {
    private String produtoId;
    private Integer quantidade;
    private BigDecimal precoUnitario;
}