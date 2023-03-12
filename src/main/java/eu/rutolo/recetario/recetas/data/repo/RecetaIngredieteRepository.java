package eu.rutolo.recetario.recetas.data.repo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import eu.rutolo.recetario.recetas.model.Receta;
import eu.rutolo.recetario.recetas.model.RecetaIngrediente;

public interface RecetaIngredieteRepository extends JpaRepository<RecetaIngrediente, UUID> {
	
	@Query("SELECT ri FROM RecetaIngrediente ri WHERE ri.receta = ?1")
	List<RecetaIngrediente> findByReceta(Receta r);
}
