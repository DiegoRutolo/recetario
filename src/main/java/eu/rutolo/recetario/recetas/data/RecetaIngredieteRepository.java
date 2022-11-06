package eu.rutolo.recetario.recetas.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import eu.rutolo.recetario.recetas.model.Receta;
import eu.rutolo.recetario.recetas.model.RecetaIngrediente;
import eu.rutolo.recetario.recetas.model.RecetaIngredienteId;

@Repository
public interface RecetaIngredieteRepository extends JpaRepository<RecetaIngrediente, RecetaIngredienteId> {
	
	@Query("SELECT ri FROM RecetaIngrediente ri WHERE ri.receta = ?1")
	List<RecetaIngrediente> findByReceta(Receta r);
}
