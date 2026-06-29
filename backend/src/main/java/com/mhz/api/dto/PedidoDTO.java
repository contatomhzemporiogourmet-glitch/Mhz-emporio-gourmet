package com.mhz.api.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {
    private String id;
    private String numeroPedido;
    private String status;
    private BigDecimal valorSubtotal;
    private BigDecimal valorDesconto;
    private BigDecimal valorFrete;
    private BigDecimal valorTotal;
    private String observacoes;
    private String enderecoEntrega;
    private String rastreamento;
    private LocalDateTime dataPedido;
    private LocalDateTime dataConfirmacao;
    private LocalDateTime dataEnvio;
    private LocalDateTime dataEntrega;
    private ClienteDTO cliente;
}