package eu.rutolo.recetario.recetas.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Type;

import lombok.Data;

@Entity
@Data
public class RecetaIngrediente {

	@Id @GeneratedValue(generator = "uuid2")
    @Type(type = "uuid-char")
    private UUID id;

	@ManyToOne(optional = false)
	private Receta receta;

	@ManyToOne(optional = true)
	private Ingrediente ingrediente;

	private Float cantidad;

	private String texto;
}
