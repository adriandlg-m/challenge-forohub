package com.alura.forohub.domain.topico;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    boolean existsByTitulo(String titulo);
    boolean existsByTituloAndIdNot(String titulo, Long id);
}