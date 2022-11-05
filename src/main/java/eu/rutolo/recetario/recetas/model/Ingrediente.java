package eu.rutolo.recetario.recetas.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
@ToString
public class Ingrediente {
	
	@Id @GeneratedValue(generator = "uuid2")
	@Type(type = "uuid-char")
	private UUID id;

	private String nombre;
	
	/** La forma más típica de medir este ingrediente */
	@Enumerated(EnumType.STRING)
	private TipoCantidad tipoCantidad;

	@Lob
	@ToString.Exclude
	private byte[] foto;
}
