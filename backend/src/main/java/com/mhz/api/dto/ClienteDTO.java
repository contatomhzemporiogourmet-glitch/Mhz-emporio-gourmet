package com.mhz.api.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {
    private String id;
    private String telefone;
    private String cpf;
    private LocalDate dataNascimento;
    private String genero;
    private String endereco;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    private Integer pontosFidelidade;
    private UsuarioDTO usuario;
}