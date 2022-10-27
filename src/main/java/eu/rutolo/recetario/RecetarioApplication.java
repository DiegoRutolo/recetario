package eu.rutolo.recetario;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import eu.rutolo.recetario.security.users.Usuario;
import eu.rutolo.recetario.security.users.UsuarioRepository;

@SpringBootApplication
public class RecetarioApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecetarioApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			usuarioRepository.save(new Usuario("admin", passwordEncoder.encode("admin"), true, "ROL_USER,ROL_ADMIN"));
			usuarioRepository.save(new Usuario("user", passwordEncoder.encode("password"), true, "ROL_USER"));
		};
	}

}
