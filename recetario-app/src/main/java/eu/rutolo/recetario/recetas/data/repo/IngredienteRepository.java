package eu.rutolo.recetario.recetas.data.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import eu.rutolo.recetario.recetas.model.Ingrediente;

public interface IngredienteRepository extends JpaRepository<Ingrediente, UUID> {
	
}
