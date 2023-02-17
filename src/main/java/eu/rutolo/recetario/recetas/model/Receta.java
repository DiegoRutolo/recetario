package eu.rutolo.recetario.recetas.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Type;

import eu.rutolo.recetario.security.users.Usuario;
import lombok.Data;

@Entity
@Data
public class Receta implements Serializable {
    
    @Id @GeneratedValue(generator = "uuid2")
    @Type(type = "uuid-char")
    private UUID id;

    private String nombre;

    @Column(length = 1024)
    private String descripcion;

    @ManyToOne
    private Usuario creador;

    @Lob
	private byte[] foto;

    @ManyToMany(targetEntity = RecetaIngrediente.class)
    private Set<RecetaIngrediente> ingredientes = new HashSet<>();

}
