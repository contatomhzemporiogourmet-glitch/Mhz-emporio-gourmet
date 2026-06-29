package com.mhz.api.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "compras")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "fornecedor_id", nullable = false)
    private Fornecedor fornecedor;

    @Column(name = "numero_compra", unique = true)
    private String numeroCompra;

    @Column(nullable = false)
    private String status = "pendente";

    @Column(name = "valor_total")
    private BigDecimal valorTotal;

    @Column(name = "data_compra")
    private LocalDateTime dataCompra = LocalDateTime.now();

    @Column(name = "data_recebimento")
    private LocalDateTime dataRecebimento;

    private String observacoes;

    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL)
    private List<ItemCompra> itens;
}