package com.alura.forohub.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DatosRegistroUsuario(
        @NotBlank
        @Email
        String login,

        @NotBlank
        String clave,

        @NotBlank
        String nombre
) {
}
