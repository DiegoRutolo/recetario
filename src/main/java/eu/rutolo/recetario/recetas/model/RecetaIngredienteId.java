package eu.rutolo.recetario.recetas.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Embeddable;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@Data @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
public class RecetaIngredienteId implements Serializable {

	private static final long serialVersionUID = 1L;

	@Type(type = "uuid-char")
	private UUID recetaId;

	@Type(type = "uuid-char")
	private UUID ingredienteId;
	
}
