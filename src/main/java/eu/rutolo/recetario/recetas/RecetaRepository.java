package eu.rutolo.recetario.recetas;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import eu.rutolo.recetario.security.users.Usuario;

@Repository
public interface RecetaRepository extends JpaRepository<Receta, Long> {

	@Query("SELECT r FROM Receta r WHERE r.creador = ?1")
	List<Receta> findRecetasByCreador(Usuario creador);
}
