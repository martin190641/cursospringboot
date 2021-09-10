package pe.todotic.cursospringboot.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Inscripcion {
    @Id
    private Integer idinscripcion;
    @JoinColumn(name = "idcurso", referencedColumnName = "idcurso")
    @ManyToOne
    private Curso curso;
    @JoinColumn(name = "idusuario")
    @ManyToOne
    private Usuario usuario;
    private LocalDateTime fechaIncripcion;

    @PrePersist
    private void setFechaInscripcion(){
        this.fechaIncripcion = LocalDateTime.now();
    }
}
