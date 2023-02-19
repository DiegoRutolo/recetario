package eu.rutolo.recetario.recetas.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import lombok.Data;

@Entity
@Data
public class Paso implements Serializable {
    
    @EmbeddedId
	private RecetaPasoId id;
    
    @ManyToOne @MapsId("recetaId")
	private Receta receta;

    private String descripcion;
}
