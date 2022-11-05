package eu.rutolo.recetario;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import eu.rutolo.recetario.recetas.Receta;
import eu.rutolo.recetario.recetas.RecetaRepository;
import eu.rutolo.recetario.security.users.Usuario;
import eu.rutolo.recetario.security.users.UsuarioRepository;

@SpringBootApplication
public class RecetarioApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecetarioApplication.class, args);
	}

	@Bean
	//@Profile("ninguno")
	public CommandLineRunner commandLineRunner(RecetaRepository recetaRepository, UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			usuarioRepository.save(new Usuario("admin", passwordEncoder.encode("admin"), true, true, true));
			usuarioRepository.save(new Usuario("user", passwordEncoder.encode("password"), true, true, false));

			recetaRepository.save(new Receta(1l, "Patatas fritas", null));
			recetaRepository.save(new Receta(2l, "Patatas cocidas", null));
		};
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
