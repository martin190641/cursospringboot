package pe.todotic.cursospringboot.model;

import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.*;

@Data
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuario")
    private Integer id;
    @NotBlank
    @Column(length = 50, nullable = false)
    private String nombres;
    @NotBlank
    @Column(length = 50, nullable = false)
    private String apellidos;
    @Column(name = "nom_completo", length = 100)
    private String nombreCompleto;
    @Column(length = 50, nullable = false, unique = true)
    @Email(regexp = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$")
    private String email;
    @Column(length = 250)
    private String password;
    @Pattern(regexp="^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "La contraseña debe tener mínimo ocho caracteres, al menos una letra y un número")
    @NotBlank
    @Transient
    private String password1;
    @Pattern(regexp="^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "La contraseña debe tener mínimo ocho caracteres, al menos una letra y un número")
    @NotBlank
    @Transient
    private String confirmPassword;
    @Enumerated(EnumType.STRING)
    private Rol rol;

    public enum Rol{
        ADMIN, ESTUDIANTE
    }

    @PreUpdate
    private void setUpdateNombreCompleto(){
        this.nombreCompleto = this.nombres + " " + this.apellidos;
    }

    @PrePersist
    private void setNombreCompletoAndEncriptarPassword(){
        this.nombreCompleto = this.nombres + " " + this.apellidos;
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
        this.password = encoder.encode(this.password1);
        this.rol = Rol.ESTUDIANTE;
    }

    public boolean isPasswordEquals(){
        return !this.getPassword1().equals(this.getConfirmPassword());
    }

}
