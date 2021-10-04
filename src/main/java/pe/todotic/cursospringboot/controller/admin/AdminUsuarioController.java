package pe.todotic.cursospringboot.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pe.todotic.cursospringboot.model.Usuario;
import pe.todotic.cursospringboot.repository.UsuarioRepository;

import javax.persistence.EntityNotFoundException;

@Controller
@RequestMapping("/admin/usuarios")
public class AdminUsuarioController {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("")
    String index(Model model, @PageableDefault(size = 15, sort = "nombreCompleto")Pageable pageable,
                 @RequestParam(required = false) String nombreCompleto) {

        Page<Usuario> usuarios;
        if (nombreCompleto != null && !nombreCompleto.trim().isEmpty()){
            usuarios = usuarioRepository.findByNombreCompletoContaining(nombreCompleto, pageable);
        }else{
            usuarios = usuarioRepository.findAll(pageable);
        }

        model.addAttribute("usuarios", usuarios);
        return "admin/usuarios";
    }

    @GetMapping("/registro")
    String registar(Model model){
        model.addAttribute("usuario", new Usuario());
        return "admin/usuario_nuevo";
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
            return "admin/usuario_nuevo";
        }
        usuarioRepository.save(usuario);
        redirectAttributes.addFlashAttribute("msgExito", "Se ha registrado exitosamente.");
        return "redirect:/admin/usuarios";
    }


    @PostMapping("/{id}/actualizar")
    String actualizar(@PathVariable Integer id, @RequestBody Usuario usuario){
        Usuario usuarioFromBD = usuarioRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        usuarioFromBD.setApellidos(usuario.getApellidos());
        usuarioFromBD.setNombres(usuario.getNombres());
        usuarioFromBD.setEmail(usuario.getEmail());
        usuarioFromBD.setPassword(usuario.getPassword());
        usuarioFromBD.setRol(usuario.getRol() );
        usuarioRepository.save(usuarioFromBD);
        return "redirect:/admin/usuarios";
    }

    @PostMapping("/{id}/eliminar")
    String eliminar(@PathVariable Integer id, RedirectAttributes ra){
        Usuario usuarioFromBD = usuarioRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        usuarioRepository.delete(usuarioFromBD);
        ra.addFlashAttribute("msgExito", "El usuario ha sido eliminado.");
        return "redirect:/admin/usuarios";
    }
}
