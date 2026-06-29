package com.mhz.api.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "fornecedores")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Fornecedor {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String nome;

    @Column(unique = true)
    private String cnpj;

    private String email;
    private String telefone;
    private String endereco;
    private String cidade;
    private String estado;
    private String cep;

    @Column(name = "custo_medio")
    private BigDecimal custoMedio = BigDecimal.ZERO;

    private BigDecimal rentabilidade = BigDecimal.ZERO;
    private Boolean ativo = true;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao = LocalDateTime.now();

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao = LocalDateTime.now();

    @OneToMany(mappedBy = "fornecedor")
    private List<Produto> produtos;

    @OneToMany(mappedBy = "fornecedor")
    private List<Compra> compras;
}