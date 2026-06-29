package com.mhz.api.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "produtos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "marca_id")
    private Marca marca;

    @ManyToOne
    @JoinColumn(name = "fornecedor_id")
    private Fornecedor fornecedor;

    @Column(nullable = false)
    private String nome;

    private String descricao;

    @Column(name = "descricao_detalhada", columnDefinition = "TEXT")
    private String descricaoDetalhada;

    @Column(unique = true)
    private String slug;

    @Column(nullable = false)
    private BigDecimal preco;

    @Column(name = "preco_custo")
    private BigDecimal precoCusto;

    private BigDecimal margem;
    private Integer estoque = 0;
    private Boolean disponivel = true;
    private BigDecimal peso;
    private String dimensoes;

    @Column(name = "imagem_principal_url")
    private String imagemPrincipalUrl;

    private Boolean ativo = true;

    @Column(name = "views_count")
    private Integer viewsCount = 0;

    @Column(name = "favoritos_count")
    private Integer favoritosCount = 0;

    @Column(name = "avaliacoes_media")
    private BigDecimal avaliacaoMedia = BigDecimal.ZERO;

    @Column(name = "seo_title")
    private String seoTitle;

    @Column(name = "seo_description")
    private String seoDescription;

    @Column(name = "seo_keywords")
    private String seoKeywords;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao = LocalDateTime.now();

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao = LocalDateTime.now();

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL)
    private List<ImagemProduto> imagens;

    @OneToOne(mappedBy = "produto", cascade = CascadeType.ALL)
    private Estoque estoqueInfo;

    @OneToMany(mappedBy = "produto")
    private List<Avaliacao> avaliacoes;
}