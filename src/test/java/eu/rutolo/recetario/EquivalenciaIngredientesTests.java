package eu.rutolo.recetario;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import eu.rutolo.recetario.recetas.data.IngredienteService;
import eu.rutolo.recetario.recetas.model.Ingrediente;
import eu.rutolo.recetario.recetas.model.IngredienteUsuario;
import eu.rutolo.recetario.recetas.model.TipoCantidad;
import eu.rutolo.recetario.security.users.UserDetailsServiceImpl;
import eu.rutolo.recetario.security.users.Usuario;

@SpringBootTest
@TestPropertySource(locations = {"classpath:application-test.properties"})
@TestInstance(Lifecycle.PER_CLASS)
public class EquivalenciaIngredientesTests {

	@Autowired
	UserDetailsServiceImpl userService;

	@Autowired
	IngredienteService ingredienteService;

	private static Map<String, Ingrediente> ingredientes = new HashMap<>();

	@BeforeAll
	public void setup() {
		// Usuarios
		Usuario abelino = new Usuario();
		abelino.setUsername("abelino");
		userService.save(abelino);

		Usuario bob = new Usuario();
		bob.setUsername("bob");
		userService.save(bob);

		Usuario carla = new Usuario();
		carla.setUsername("carla");
		userService.save(carla);

		IngredienteUsuario patatasAbelino = new IngredienteUsuario();
		patatasAbelino.setNombre("Patatas");
		patatasAbelino.setTipoCantidad(TipoCantidad.UD);
		patatasAbelino.setUsuario(abelino);
		ingredientes.put("patatasAbelino", ingredienteService.save(patatasAbelino));

		IngredienteUsuario patatasBob = new IngredienteUsuario();
		patatasBob.setNombre("Patatas");
		patatasBob.setTipoCantidad(TipoCantidad.UD);
		patatasBob.setUsuario(bob);
		ingredientes.put("patatasBob", ingredienteService.save(patatasBob));

		IngredienteUsuario patatasCarla = new IngredienteUsuario();
		patatasCarla.setNombre("Patatas");
		patatasCarla.setTipoCantidad(TipoCantidad.G);
		patatasCarla.setUsuario(carla);
		ingredientes.put("patatasCarla", ingredienteService.save(patatasCarla));

		IngredienteUsuario cebollaBob = new IngredienteUsuario();
		cebollaBob.setNombre("Cebolla");
		cebollaBob.setTipoCantidad(TipoCantidad.UD);
		cebollaBob.setUsuario(bob);
		ingredientes.put("cebollaBob", ingredienteService.save(cebollaBob));
	}

	@Test
	public void esEquivalenteTest() {
		assertTrue(ingredienteService.isEquivalente(ingredientes.get("patatasAbelino"), ingredientes.get("patatasBob")), "Patatas iguales");
		assertFalse(ingredienteService.isEquivalente(ingredientes.get("patatasAbelino"), ingredientes.get("patatasCarla")), "Patatas distintas por tipoCantidad");
		assertFalse(ingredienteService.isEquivalente(ingredientes.get("patatasAbelino"), ingredientes.get("cebollaBob")), "Patatas y cebollas");
	}
	
}
