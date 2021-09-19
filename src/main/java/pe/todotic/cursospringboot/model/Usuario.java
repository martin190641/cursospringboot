package pe.todotic.cursospringboot.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;

@Data
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuario")
    private Integer id;
    @NotEmpty
    @Column(length = 50, nullable = false)
    private String nombres;
    @NotNull
    @Column(length = 50, nullable = false)
    private String apellidos;
    @Column(name = "nom_completo", length = 100)
    private String nombreCompleto;
    @Column(length = 50, nullable = false, unique = true)
    @Email(regexp = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$")
    private String email;
    @Column(length = 250)
    @Pattern(regexp="^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "La contraseña debe tener mínimo ocho caracteres, al menos una letra y un número")
    private String password;
    @Transient
//    @Pattern(regexp="^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "La contraseña debe tener mínimo ocho caracteres, al menos una letra y un número")
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

    public boolean isPasswordEquals(){
        return !this.getPassword().equals(this.getConfirmPassword());
    }

}
