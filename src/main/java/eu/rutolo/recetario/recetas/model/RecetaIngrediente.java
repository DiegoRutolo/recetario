package eu.rutolo.recetario.recetas.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import lombok.Data;

@Entity
@Data
public class RecetaIngrediente {
	
	@EmbeddedId
	private RecetaIngredienteId id;

	@ManyToOne @MapsId("recetaId") @JoinColumn(name = "receta_id")
	private Receta receta;

	@ManyToOne @MapsId("ingredienteId") @JoinColumn(name = "ingrediente_id")
	private Ingrediente ingrediente;

	private Float cantidad;
}
