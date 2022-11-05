package eu.rutolo.recetario.recetas.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import eu.rutolo.recetario.security.users.Usuario;
import lombok.Data;

@Entity
@Data
public class Receta implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(length = 1024)
    private String descripcion;

    @ManyToOne
    private Usuario creador;

    @Lob
	private byte[] foto;

}
