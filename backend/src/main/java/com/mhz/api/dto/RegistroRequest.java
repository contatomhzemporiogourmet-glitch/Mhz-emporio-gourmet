package com.mhz.api.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistroRequest {
    private String email;
    private String nome;
    private String senha;
    private String telefone;
    private String cpf;
}