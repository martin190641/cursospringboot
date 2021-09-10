package pe.todotic.cursospringboot.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcurso")
    private Integer id;
    private String titulo;
    private String descripcion;
    private String rutaImagen;
    private Float precio;
    private LocalDateTime fechaCreacion;
    @Column(name = "fecha_act")
    private LocalDateTime fecaActualizacion;

    @PrePersist
    private void setFechaCreacio(){
        this.fechaCreacion = LocalDateTime.now();
    }

    @PreUpdate
    private void setFecaActualizacion(){
        this.fecaActualizacion = LocalDateTime.now();
    }
}
