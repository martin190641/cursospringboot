package pe.todotic.cursospringboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pe.todotic.cursospringboot.model.Usuario;
import pe.todotic.cursospringboot.repository.UsuarioRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("")
    String index(Model model) {
        List<Usuario> usuarios = usuarioRepository.findAll();

        model.addAttribute("usuarios", usuarios);
        return "usuarios";
    }

    @GetMapping("/registro")
    String registar(Model model){
        model.addAttribute("usuario", new Usuario());
        return "usuario_registrar";
    }

    @PostMapping("/registro")
    String registar(@Validated Usuario usuario, BindingResult bindingResult, Model model,
                         RedirectAttributes redirectAttributes){
        usuario.setRol(Usuario.Rol.ESTUDIANTE);
        if (usuario.isPasswordEquals()) {
            bindingResult.rejectValue("confirmPassword", "", "Las contraseñas no coinciden");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("usuario", usuario);
            return "usuario_registrar";
        }
        usuarioRepository.save(usuario);
        redirectAttributes.addFlashAttribute("msgExito", "Se ha registrado exitosamente.");
        return "redirect:/cursos";
    }

    @ResponseBody
    @PutMapping("/{id}/actualizar")
    Usuario actualizar(@PathVariable Integer id, @RequestBody Usuario usuario){
        Usuario usuarioFromBD = usuarioRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        usuarioFromBD.setApellidos(usuario.getApellidos());
        usuarioFromBD.setNombres(usuario.getNombres());
        usuarioFromBD.setEmail(usuario.getEmail());
        usuarioFromBD.setPassword(usuario.getPassword());
        usuarioFromBD.setRol(usuario.getRol() );
        return usuarioRepository.save(usuarioFromBD);
    }

    @PostMapping("/{id}/eliminar")
    String eliminar(@PathVariable Integer id, RedirectAttributes ra){
        Usuario usuarioFromBD = usuarioRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        usuarioRepository.delete(usuarioFromBD);
        ra.addFlashAttribute("msgExito", "El usuario ha sido eliminado.");
        return "redirect:/usuarios";
    }
}
