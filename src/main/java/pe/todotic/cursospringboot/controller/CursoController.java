package pe.todotic.cursospringboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pe.todotic.cursospringboot.model.Curso;
import pe.todotic.cursospringboot.model.Usuario;
import pe.todotic.cursospringboot.repository.CursoRepository;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoRepository cursoRepository;
    // http://localhost:8080?nombre=Darwin
    @GetMapping("")
    String index(Model model) {
        List<Curso> cursos = cursoRepository.findAll();

        model.addAttribute("cursos", cursos);
        return "index";
    }

    @ResponseBody
    @PostMapping("/crear")
    Curso crear(@RequestBody Curso curso){
        /*Curso curso = new Curso();
        curso.setTitulo("Python");
        curso.setDescripcion("Curso de Python desde cero");
        curso.setPrecio(99f);
        curso.setFechaCreacion(LocalDateTime.now());*/
        return cursoRepository.save(curso);
    }

    @ResponseBody
    @PutMapping("/{id}/actualizar")
    Curso actualizar(@PathVariable Integer id, @RequestBody Curso curso){
        /*Curso curso = new Curso();
        curso.setTitulo("Python");
        curso.setDescripcion("Curso de Python desde cero");
        curso.setPrecio(99f);
        curso.setFechaCreacion(LocalDateTime.now());*/
        Curso cursoDB = cursoRepository.getById(id);
        curso.setId(id);
        return cursoRepository.save(curso);
    }




}
