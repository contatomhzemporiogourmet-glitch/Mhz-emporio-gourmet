package com.mhz.api.dto;

import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoCreateUpdateDTO {
    private String nome;
    private String descricao;
    private String descricaoDetalhada;
    private BigDecimal preco;
    private BigDecimal precoCusto;
    private Integer estoque;
    private String imagemPrincipalUrl;
    private String categoriaId;
    private String marcaId;
    private String fornecedorId;
    private String seoTitle;
    private String seoDescription;
    private String seoKeywords;
}