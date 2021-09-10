package pe.todotic.cursospringboot.model;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuario")
    private Integer id;
    @Column(length = 50)
    private String nombres;
    @Column(length = 50)
    private String apellidos;
    @Column(name = "nom_completo", length = 100)
    private String nombreCompleto;
    @Column(length = 50)
    private String email;
    @Column(length = 250)
    private String password;
    @Enumerated(EnumType.STRING)
    private Rol rol;

    public enum Rol{
        ADMIN, ESTUDIANTE
    }

    @PrePersist
    @PreUpdate
    private void setNombreCompleto(){
        this.nombreCompleto = this.nombres + " " + this.apellidos;
    }
}
