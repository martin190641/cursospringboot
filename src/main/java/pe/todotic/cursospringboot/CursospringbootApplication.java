package pe.todotic.cursospringboot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pe.todotic.cursospringboot.repository.CursoRepository;

@SpringBootApplication
public class CursospringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(CursospringbootApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(CursoRepository cursoRepository) {
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
				/*Stream.of(
						new Curso("Python"),
						new Curso("Django"),
						new Curso("Full stack")
				)
						.map(cursoRepository::save)
						.forEach(System.out::println);*/

/*				Stream.of(new Usuario("juanito@gmail.com", "juan123"), new Usuario("jaimito@hotmail.com", "jato123"))
						.map(usuarioRepository::save).
						forEach(System.out::println);*/
			}
		};
	}

}
