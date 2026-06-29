package com.mhz.api.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pedidos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Column(name = "numero_pedido", unique = true, nullable = false)
    private String numeroPedido;

    @Column(nullable = false)
    private String status = "pendente";

    @Column(name = "valor_subtotal")
    private BigDecimal valorSubtotal;

    @Column(name = "valor_desconto")
    private BigDecimal valorDesconto = BigDecimal.ZERO;

    @Column(name = "valor_frete")
    private BigDecimal valorFrete = BigDecimal.ZERO;

    @Column(name = "valor_total")
    private BigDecimal valorTotal;

    @Column(name = "cupom_id")
    private String cupomId;

    private String observacoes;

    @Column(name = "endereco_entrega")
    private String enderecoEntrega;

    private String rastreamento;

    @Column(name = "data_pedido")
    private LocalDateTime dataPedido = LocalDateTime.now();

    @Column(name = "data_confirmacao")
    private LocalDateTime dataConfirmacao;

    @Column(name = "data_envio")
    private LocalDateTime dataEnvio;

    @Column(name = "data_entrega")
    private LocalDateTime dataEntrega;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao = LocalDateTime.now();

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemPedido> itens;

    @OneToMany(mappedBy = "pedido")
    private List<Pagamento> pagamentos;
}