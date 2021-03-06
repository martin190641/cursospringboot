package pe.todotic.cursospringboot.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.todotic.cursospringboot.model.Curso;

import java.util.List;


@Repository
public interface CursoRepository extends JpaRepository<Curso, Integer> {
    Page<Curso> findByTituloContaining(String titulo, Pageable pageable);

    List<Curso> findTop8ByOrderByFechaCreacionDesc();
}
