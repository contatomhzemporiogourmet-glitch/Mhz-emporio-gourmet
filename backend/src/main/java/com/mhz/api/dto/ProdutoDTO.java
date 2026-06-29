package com.mhz.api.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {
    private String id;
    private String nome;
    private String descricao;
    private String descricaoDetalhada;
    private String slug;
    private BigDecimal preco;
    private BigDecimal precoCusto;
    private BigDecimal margem;
    private Integer estoque;
    private Boolean disponivel;
    private String imagemPrincipalUrl;
    private Integer viewsCount;
    private Integer favoritosCount;
    private BigDecimal avaliacaoMedia;
    private String categoriaId;
    private String categoriaNome;
    private String marcaId;
    private String marcaNome;
    private String fornecedorId;
    private LocalDateTime dataCriacao;
}