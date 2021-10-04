package pe.todotic.cursospringboot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.todotic.cursospringboot.model.Curso;
import pe.todotic.cursospringboot.model.Inscripcion;
import pe.todotic.cursospringboot.model.Usuario;

import java.util.Optional;

@Repository
public interface InscripcionRepository extends JpaRepository<Inscripcion, Integer> {
    Page<Curso> findInscripcionByUsuario(Optional<Usuario> usuario, Pageable pageable);

}
