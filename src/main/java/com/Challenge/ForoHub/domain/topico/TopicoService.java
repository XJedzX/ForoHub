package com.Challenge.ForoHub.domain.topico;

import com.Challenge.ForoHub.domain.usuario.UsuarioRepository;
import com.Challenge.ForoHub.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicoService {
    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;


    public DatosRespuestaTopico topicoCreado(TopicoDTO topico){
        if (!usuarioRepository.findById(topico.autor_Id()).isPresent()){
            throw new ValidacionDeIntegridad("Este ID de usuario no está registrado en la base de datos.");
        }
        var titulo = topico.titulo();
        if (titulo != null && topicoRepository.existsByTituloIgnoreCase(titulo)){
            throw new ValidacionDeIntegridad("Este título ya está presente en la base de datos. Por favor revise el tópico existente.");
        }
        String mensaje = topico.mensaje();
        if (mensaje != null && topicoRepository.existsByMensajeIgnoreCase(mensaje)){
            throw new ValidacionDeIntegridad("Este mensaje ya está presente en la base de datos. Por favor revise el tópico existente.");
        }
        var usuario = usuarioRepository.findById(topico.autor_Id()).get();
        var topicoId= new Topico(null,titulo,mensaje,topico.date(),Boolean.TRUE,usuario,topico.curso());
        topicoRepository.save(topicoId);
        return new DatosRespuestaTopico(topicoId);
    }
}