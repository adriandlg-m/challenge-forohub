package com.alura.forohub.domain.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosActualizacionTopico(
        @NotBlank
        //Long id,
        String titulo,
        @NotBlank
        String mensaje,
        @NotBlank
        String curso) {
}