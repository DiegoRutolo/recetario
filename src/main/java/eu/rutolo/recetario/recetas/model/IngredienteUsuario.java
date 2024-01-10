package eu.rutolo.recetario.recetas.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import eu.rutolo.recetario.security.users.Usuario;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data @EqualsAndHashCode(callSuper = true)
public class IngredienteUsuario extends Ingrediente {
	
	@ManyToOne(optional = false)
	private Usuario usuario;
}
