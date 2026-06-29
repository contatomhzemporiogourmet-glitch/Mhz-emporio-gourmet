package com.mhz.api.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "estoque")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Estoque {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToOne
    @JoinColumn(name = "produto_id", nullable = false, unique = true)
    private Produto produto;

    @Column(nullable = false)
    private Integer quantidade = 0;

    @Column(name = "quantidade_reservada")
    private Integer quantidadeReservada = 0;

    @Column(name = "quantidade_disponivel")
    private Integer quantidadeDisponivel = 0;

    @Column(name = "data_ultima_atualizacao")
    private LocalDateTime dataUltimaAtualizacao = LocalDateTime.now();
}