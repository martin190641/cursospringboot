package pe.todotic.cursospringboot.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

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
    @Email(regexp = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$")
    private String email;
    @Column(length = 250)
    @Pattern(regexp="(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$\"")
    private String password;
    @Pattern(regexp="(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$\"")
    private String confirmPassword;
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
