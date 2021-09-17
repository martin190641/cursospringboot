package pe.todotic.cursospringboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pe.todotic.cursospringboot.model.Curso;
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

    @ResponseBody
    @PostMapping("/crear")
    Usuario crear(@RequestBody Usuario usuario){
        return usuarioRepository.save(usuario);
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
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}/eliminar")
    void eliminar(@PathVariable Integer id){
        Usuario usuarioFromBD = usuarioRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        usuarioRepository.delete(usuarioFromBD);
    }
}
