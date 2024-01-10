package eu.rutolo.recetario.recetas.data.repo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import eu.rutolo.recetario.recetas.model.Ingrediente;
import eu.rutolo.recetario.recetas.model.IngredienteUsuario;
import eu.rutolo.recetario.security.users.Usuario;

public interface IngredienteUsuarioRepository extends JpaRepository<IngredienteUsuario, UUID> {
	
	List<Ingrediente> findByUsuario(Usuario usuario);
}
