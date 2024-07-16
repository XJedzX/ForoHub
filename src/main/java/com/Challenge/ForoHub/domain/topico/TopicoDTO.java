package com.Challenge.ForoHub.domain.topico;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record TopicoDTO(
        @NotNull
        String titulo,
        @NotNull
        String mensaje,
        @NotNull
        Long autor_Id,
        @NotNull
        String curso,
        LocalDateTime date
) {
}