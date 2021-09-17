package pe.todotic.cursospringboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pe.todotic.cursospringboot.model.Curso;
import pe.todotic.cursospringboot.repository.CursoRepository;
import pe.todotic.cursospringboot.service.FileSystemStorageService;

import java.util.List;

@Controller
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private FileSystemStorageService fileSystemStorageService;

    // http://localhost:8080?nombre=Darwin
    @GetMapping("")
    String index(Model model) {
        List<Curso> cursos = cursoRepository.findAll();

        model.addAttribute("cursos", cursos);

        return "index";
    }

    @GetMapping("/nuevo")
    String nuevo(Model model) {
        model.addAttribute("curso", new Curso());
        return "nuevo";
    }

    @PostMapping("/nuevo")
    String crear(@Validated Curso curso, BindingResult bindingResult, Model model, RedirectAttributes ra) {
        if (curso.getImagen().isEmpty()) {
            bindingResult.rejectValue("imagen", "MultipartNotEmpty");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("curso", curso);
            return "nuevo";
        }
        String rutaImagen = fileSystemStorageService.store(curso.getImagen());
        curso.setRutaImagen(rutaImagen);

        cursoRepository.save(curso);

        ra.addFlashAttribute("msgExito", "El curso ha sido creado correctamente.");

        return "redirect:/cursos";
    }

    @GetMapping("/{id}/editar")
    String editar(@PathVariable Integer id, Model model) {
        Curso curso = cursoRepository
                .getById(id);

        model.addAttribute("curso", curso);

        return "editar";
    }

    @PostMapping("/{id}/editar")
    String actualizar(@PathVariable Integer id, Curso curso, Model model, RedirectAttributes ra) {
        Curso cursoFromDB = cursoRepository
                .getById(id);

        if (!curso.getImagen().isEmpty()) {
            String rutaImagen = fileSystemStorageService.store(curso.getImagen());
            cursoFromDB.setRutaImagen(rutaImagen);
        }

        cursoFromDB.setTitulo(curso.getTitulo());
        cursoFromDB.setDescripcion(curso.getDescripcion());
        cursoFromDB.setPrecio(curso.getPrecio());

        cursoRepository.save(cursoFromDB);
        ra.addFlashAttribute("msgExito", "El curso ha sido actualizado correctamente.");

        return "redirect:/cursos";
    }

    @PostMapping("/{id}/eliminar")
    String eliminar(@PathVariable Integer id, RedirectAttributes ra) {
        Curso curso = cursoRepository
                .getById(id);

        cursoRepository.delete(curso);
        ra.addFlashAttribute("msgExito", "El curso ha sido eliminado.");

        return "redirect:/cursos";
    }

}
