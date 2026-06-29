package com.mhz.api.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarcaDTO {
    private String id;
    private String nome;
    private String logoUrl;
    private String slug;
    private Boolean ativo;
}