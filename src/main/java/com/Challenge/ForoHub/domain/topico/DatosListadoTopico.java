package com.Challenge.ForoHub.domain.topico;

public record DatosListadoTopico(Long id, String titulo, String mensaje, String fechaCreacion, Boolean status, String autor, String curso) {
    public DatosListadoTopico(Topico topico){
        this(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getFechaCreacion().toString(),
                topico.getStatus(), topico.getAutor().getNombre(),topico.getCurso());
    }
}
