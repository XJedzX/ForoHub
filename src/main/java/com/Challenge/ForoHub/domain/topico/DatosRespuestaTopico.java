package com.Challenge.ForoHub.domain.topico;

import java.time.LocalDateTime;

public record DatosRespuestaTopico(
        Long id,
        String title,
        String message,
        Boolean status,
        Long autor_Id,
        String autor_nombre,
        String curso,
        LocalDateTime date
) {
    public DatosRespuestaTopico(Topico  topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getStatus(),
                topico.getAutor().getId(),
                topico.getAutor().getNombre(),
                topico.getCurso(),
                topico.getFechaCreacion());
    }
}
