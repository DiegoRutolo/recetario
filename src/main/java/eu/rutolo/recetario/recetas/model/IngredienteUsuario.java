package eu.rutolo.recetario.recetas.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import eu.rutolo.recetario.security.users.Usuario;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@DiscriminatorValue("IngredienteUsuario")
@Data @EqualsAndHashCode(callSuper = true)
public class IngredienteUsuario extends Ingrediente {

	public IngredienteUsuario() { }
	
	public IngredienteUsuario(Ingrediente ingrediente, Usuario creador) {
		super();
		this.setId(ingrediente.getId());
		this.setNombre(ingrediente.getNombre());
		this.setTipoCantidad(ingrediente.getTipoCantidad());
		this.setFoto(ingrediente.getFoto());
		this.setUsuario(creador);
	}

	@ManyToOne
	private Usuario usuario;
}
