package pe.todotic.cursospringboot.controller;

import com.mercadopago.MercadoPago;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Preference;
import com.mercadopago.resources.datastructures.preference.BackUrls;
import com.mercadopago.resources.datastructures.preference.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pe.todotic.cursospringboot.model.Curso;
import pe.todotic.cursospringboot.model.Usuario;
import pe.todotic.cursospringboot.repository.CursoRepository;
import pe.todotic.cursospringboot.repository.InscripcionRepository;
import pe.todotic.cursospringboot.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

@Controller
public class CursoController {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private InscripcionRepository inscripcionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("")
    String index(Model model) {
        // obtener los últimos 8 cursos
        List<Curso> ultimosCursos = cursoRepository
                .findTop8ByOrderByFechaCreacionDesc();

        if (true) throw new RuntimeException("Error en la consulta de base de datos.");

        model.addAttribute("ultimosCursos", ultimosCursos);

        return "index";
    }

    @GetMapping("/cursos")
    String getCursos(
            Model model,
            @PageableDefault(size = 8, sort = "titulo") Pageable pageable
    ) {
        Page<Curso> cursos = cursos = cursoRepository.findAll(pageable);

        model.addAttribute("cursos", cursos);

        return "lista-cursos";
    }

    @GetMapping("/cursos/{id}")
    String getCurso(@PathVariable Integer id, Model model) throws MPException {
        Curso curso = cursoRepository
                .getById(id);

        // configuración de la preferencia de mercado pago

        MercadoPago.SDK.setAccessToken("TEST-2435260083110362-092801-5716e0ec30a64181a5c5babadace3793-141978456");

        Preference preference = new Preference();

        Item item = new Item()
                .setTitle(curso.getTitulo())
                .setQuantity(1)
                .setUnitPrice(curso.getPrecio());

        preference.appendItem(item);

        BackUrls backUrls = new BackUrls(
                "http://localhost:8080/usuario/inscribir?c=" + curso.getId(),
                "http://localhost:8080/mercadopago/pending",
                "http://localhost:8080/cursos/" + curso.getId()
        );

        preference.setBackUrls(backUrls);
        preference.save();

        model.addAttribute("curso", curso);
        model.addAttribute("preference", preference);
        return "detalles-curso";
    }

    @GetMapping("/miscursos")
    String getMisCursos(
            Model model,
            @PageableDefault(size = 8, sort = "titulo") Pageable pageable
    ) throws Exception {
        Optional<Usuario>  usuario= usuarioRepository.findByEmail("jmriosp@ufpso.edu.co");
        Page<Curso> cursos  = inscripcionRepository.findInscripcionByUsuario(usuario, pageable);

        model.addAttribute("cursos", cursos);

        return "mis_cursos";
    }

}
