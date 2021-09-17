package pe.todotic.cursospringboot.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.todotic.cursospringboot.model.Curso;


@Repository
public interface CursoRepository extends JpaRepository<Curso, Integer> {
}
