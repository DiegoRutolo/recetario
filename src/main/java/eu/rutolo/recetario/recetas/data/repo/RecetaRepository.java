package eu.rutolo.recetario.recetas.data.repo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import eu.rutolo.recetario.recetas.model.Receta;
import eu.rutolo.recetario.security.users.Usuario;

public interface RecetaRepository extends JpaRepository<Receta, UUID> {

	@Query("SELECT r FROM Receta r WHERE r.creador = ?1")
	List<Receta> findRecetasByCreador(Usuario creador);
}
