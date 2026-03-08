package com.alura.forohub.controller;

import com.alura.forohub.domain.topico.*;
import com.alura.forohub.domain.usuario.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/topicos")
@CrossOrigin(origins = "*")
public class TopicoController {

    @Autowired
    private TopicoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity registrar(@RequestBody @Valid DatosRegistroTopico datos, UriComponentsBuilder uriBuilder) {

        // VALIDACIÓN DE DUPLICADOS: Título
        if (repository.existsByTitulo(datos.titulo())) {
            return ResponseEntity.badRequest().body("Error: Ya existe un tópico con ese título.");
        }

        // Usuario que está logueado actualmente
        var usuarioAutenticado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Uso del constructor que recibe (datos, usuario)
        Topico topico = new Topico(datos, usuarioAutenticado);
        repository.save(topico);

        var datosDetalle = new DatosDetalleTopico(topico);
        var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

        return ResponseEntity.created(uri).body(datosDetalle);
    }

    @GetMapping
    public ResponseEntity<Page<DatosDetalleTopico>> listar(@PageableDefault(size = 10, sort = "fechaCreacion", direction = Sort.Direction.ASC) Pageable paginacion) {
        return ResponseEntity.ok(repository.findAll(paginacion).map(DatosDetalleTopico::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity detallar(@PathVariable Long id) {
        var topico = repository.getReferenceById(id);
        return ResponseEntity.ok(new DatosDetalleTopico(topico));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity actualizar(@PathVariable Long id, @RequestBody @Valid DatosActualizacionTopico datos) {
        var topico = repository.getReferenceById(id);
        var usuarioAutenticado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // VALIDACIÓN DE SEGURIDAD
        if (!topico.getAutor().getId().equals(usuarioAutenticado.getId())) {
            return ResponseEntity.status(403).body("No tienes permiso para editar este tópico.");
        }

        if (repository.existsByTituloAndIdNot(datos.titulo(), id)) {
            return ResponseEntity.badRequest().body("Error: Ya existe otro tópico con ese mismo título.");
        }

        topico.actualizarInformacion(datos);
        return ResponseEntity.ok(new DatosDetalleTopico(topico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminar(@PathVariable Long id) {
        var topico = repository.getReferenceById(id);
        var usuarioAutenticado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // VALIDACIÓN DE SEGURIDAD
        if (!topico.getAutor().getId().equals(usuarioAutenticado.getId())) {
            return ResponseEntity.status(403).body("No tienes permiso para eliminar este tópico.");
        }

        repository.delete(topico);
        return ResponseEntity.noContent().build();
    }
}