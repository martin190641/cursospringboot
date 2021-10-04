package pe.todotic.cursospringboot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.todotic.cursospringboot.model.Usuario;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Page<Usuario> findByNombreCompletoContaining(String nombreCompleto, Pageable pageable);
    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);
}
