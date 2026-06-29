package com.mhz.api.dto;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoCreateDTO {
    private List<ItemPedidoCreateDTO> itens;
    private String enderecoEntrega;
    private String observacoes;
    private String cupomCodigo;
    private BigDecimal valorFrete;
}