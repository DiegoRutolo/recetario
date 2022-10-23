package eu.rutolo.recetario;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import eu.rutolo.recetario.recetas.Receta;
import eu.rutolo.recetario.recetas.RecetaRepository;

@SpringBootTest
@TestPropertySource(locations = {"classpath:application-test.properties"})
class RecetarioApplicationTests {

	@Autowired
	private RecetaRepository recetaRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void crudReceta() {
		List<Receta> recetas = recetaRepository.findAll();
		assertEquals(0, recetas.size());

		Receta r1 = new Receta();
		r1.setNombre("Patatas cocidas");
		r1 = recetaRepository.save(r1);
		assertNotNull(r1.getId());

		recetas = recetaRepository.findAll();
		assertEquals(1, recetas.size());
	}

}
