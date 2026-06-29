package com.mhz.api.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FornecedorDTO {
    private String id;
    private String nome;
    private String cnpj;
    private String email;
    private String telefone;
    private String endereco;
    private String cidade;
    private String estado;
    private String cep;
    private Boolean ativo;
}