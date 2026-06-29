package com.mhz.api.dto;

import lombok.*;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private String id;
    private String email;
    private String nome;
    private String tipoUsuario;
    private LocalDateTime dataCriacao;
    private Boolean ativo;
    private Set<String> roles;
}