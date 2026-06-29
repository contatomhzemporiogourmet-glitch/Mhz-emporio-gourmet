package com.mhz.api.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarrinhoItemDTO {
    private String id;
    private String produtoId;
    private String produtoNome;
    private Integer quantidade;
    private java.math.BigDecimal precoUnitario;
    private java.math.BigDecimal subtotal;
}