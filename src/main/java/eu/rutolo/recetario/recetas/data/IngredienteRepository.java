package eu.rutolo.recetario.recetas.data;

import org.springframework.data.jpa.repository.JpaRepository;

import eu.rutolo.recetario.recetas.model.Ingrediente;

public interface IngredienteRepository extends JpaRepository<Ingrediente, Long> {
	
}
