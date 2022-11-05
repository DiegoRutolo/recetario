package eu.rutolo.recetario.recetas.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import lombok.Data;

@Entity
@Data
public class RecetaIngrediente {
	
	@EmbeddedId
	private RecetaIngredienteId id;

	@ManyToOne @MapsId("recetaId")
	private Receta receta;

	@ManyToOne @MapsId("ingredienteId")
	private Ingrediente ingrediente;

	private Float cantidad;
}
