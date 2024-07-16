package com.Challenge.ForoHub.controller;

import com.Challenge.ForoHub.domain.topico.*;
import com.Challenge.ForoHub.infra.errores.ValidacionDeIntegridad;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/topico")
@SecurityRequirement(name="bearer-key")
public class TopicoController {
    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private TopicoService topicoService;

    public TopicoController(TopicoRepository topicoRepository) {
        this.topicoRepository = topicoRepository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity topicoRegistrado(@RequestBody @Valid TopicoDTO datos, UriComponentsBuilder uriComponentsBuilder) throws ValidacionDeIntegridad {
        var topicoRegistrado = topicoService.topicoCreado(datos);
        URI url=uriComponentsBuilder.path("/topico/{id}").buildAndExpand(topicoRegistrado.id()).toUri();
        return ResponseEntity.created(url).body(datos);
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoTopico>> listadoTopico(@PageableDefault(size = 5) Pageable pag){
        return ResponseEntity.ok(topicoRepository.findAll(pag).map(DatosListadoTopico::new));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DatosRespuestaTopico> actualizarTopico(@RequestBody @Valid DatosActualizarTopico datosActualizarTopico){
        Topico topico=topicoRepository.getReferenceById(datosActualizarTopico.id());
        topico.actualizarDatos(datosActualizarTopico);
        return ResponseEntity.ok(new DatosRespuestaTopico(topico.getId(), topico.getTitulo(),topico.getMensaje(),
                topico.getStatus(),topico.getAutor().getId(),topico.getAutor().getNombre(),
                topico.getCurso(),topico.getFechaCreacion()));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> eliminarTopico(@PathVariable Long id){
        Optional<Topico> topicoOptional = topicoRepository.findById(id);
        if (topicoOptional.isPresent()) {
            topicoRepository.delete(topicoOptional.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaTopico> obtenerTopicoId(@PathVariable Long id){
        Topico topico=topicoRepository.getReferenceById(id);
        var datosTopico=new DatosRespuestaTopico(topico.getId(), topico.getTitulo(),topico.getMensaje(),
                topico.getStatus(),topico.getAutor().getId(),topico.getAutor().getNombre(),
                topico.getCurso(),topico.getFechaCreacion());
        return ResponseEntity.ok(datosTopico);
    }
}
