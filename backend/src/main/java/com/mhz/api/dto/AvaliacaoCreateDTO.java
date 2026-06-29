package com.mhz.api.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvaliacaoCreateDTO {
    private String titulo;
    private String descricao;
    private Integer nota;
}