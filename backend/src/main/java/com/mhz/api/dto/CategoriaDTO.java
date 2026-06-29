package com.mhz.api.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDTO {
    private String id;
    private String nome;
    private String descricao;
    private String imagemUrl;
    private String slug;
    private Boolean ativo;
}