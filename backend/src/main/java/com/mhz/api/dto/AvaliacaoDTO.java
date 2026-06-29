package com.mhz.api.dto;

import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvaliacaoDTO {
    private String id;
    private String titulo;
    private String descricao;
    private Integer nota;
    private String produtoId;
    private String clienteId;
}