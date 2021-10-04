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
import pe.todotic.cursospringboot.model.Curso;
import pe.todotic.cursospringboot.repository.CursoRepository;
import pe.todotic.cursospringboot.service.FileSystemStorageService;

@Controller
@RequestMapping("/admin/cursos")
public class AdminCursoController {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private FileSystemStorageService fileSystemStorageService;

    @GetMapping("")
    String index(
            Model model,
            @PageableDefault(size = 5, sort = "titulo") Pageable pageable,
            @RequestParam(required = false) String titulo
    ) {
        Page<Curso> cursos;

        if (titulo != null && !titulo.trim().isEmpty()) {
            cursos = cursoRepository.findByTituloContaining(titulo, pageable);
        } else {
            cursos = cursoRepository.findAll(pageable);
        }

        model.addAttribute("cursos", cursos);

        return "admin/index";
    }

    @GetMapping("/nuevo")
    String nuevo(Model model) {
        model.addAttribute("curso", new Curso());
        return "admin/nuevo";
    }

    @PostMapping("/nuevo")
    String crear(@Validated Curso curso, BindingResult bindingResult, Model model, RedirectAttributes ra) {
        if (curso.getImagen().isEmpty()) {
            bindingResult.rejectValue("imagen", "MultipartNotEmpty");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("curso", curso);
            return "admin/nuevo";
        }
        String rutaImagen = fileSystemStorageService.store(curso.getImagen());
        curso.setRutaImagen(rutaImagen);

        cursoRepository.save(curso);

        ra.addFlashAttribute("msgExito", "El curso ha sido creado correctamente.");

        return "redirect:/admin/cursos";
    }

    @GetMapping("/{id}/editar")
    String editar(@PathVariable Integer id, Model model) {
        Curso curso = cursoRepository
                .getById(id);

        model.addAttribute("curso", curso);

        return "admin/editar";
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

        return "redirect:/admin/cursos";
    }

    @PostMapping("/{id}/eliminar")
    String eliminar(@PathVariable Integer id, RedirectAttributes ra) {
        Curso curso = cursoRepository
                .getById(id);

        cursoRepository.delete(curso);
        ra.addFlashAttribute("msgExito", "El curso ha sido eliminado.");

        return "redirect:/admin/cursos";
    }

}
