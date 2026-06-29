package com.mhz.api.dto;

import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CupomDTO {
    private String id;
    private String codigo;
    private String descricao;
    private String tipo;
    private BigDecimal valor;
    private Integer limiteUso;
    private Integer usos;
    private Boolean ativo;
}