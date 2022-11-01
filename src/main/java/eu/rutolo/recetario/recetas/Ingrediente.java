package eu.rutolo.recetario.recetas;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Ingrediente {
	
	@Id @GeneratedValue
	private Long id;

	private String nombre;
	
	/** La forma más típica de medir este ingrediente */
	@Enumerated(EnumType.STRING)
	private TipoCantidad tipoCantidad;

	@Lob
	private byte[] foto;
}
