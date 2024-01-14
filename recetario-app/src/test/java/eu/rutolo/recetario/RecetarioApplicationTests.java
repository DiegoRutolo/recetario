package eu.rutolo.recetario;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import eu.rutolo.recetario.recetas.data.repo.RecetaRepository;
import eu.rutolo.recetario.recetas.model.Receta;

@SpringBootTest
@TestPropertySource(locations = {"classpath:application-test.properties"})
class RecetarioApplicationTests {

	private final int TAMANHO_INICIAL = 0;

	@Autowired
	private RecetaRepository recetaRepository;

	@Test
	void contextLoads() {
		assertNotNull(recetaRepository);

		List<Receta> recetas = recetaRepository.findAll();
		assertEquals(TAMANHO_INICIAL, recetas.size());
	}

	@Test
	void crudReceta() {
		// Create
		String nombreInicial = "Patatas asadas";
		Receta r1 = new Receta();
		r1.setNombre(nombreInicial);
		r1 = recetaRepository.save(r1);
		List<Receta> recetas = recetaRepository.findAll();
		assertEquals(TAMANHO_INICIAL+1, recetas.size());

		// Read
		assertNotNull(r1.getId());
		assertEquals(nombreInicial, r1.getNombre());

		// Update
		String nombreNuevo = "Patatas panadera";
		r1.setNombre(nombreNuevo);
		r1 = recetaRepository.save(r1);
		assertEquals(nombreNuevo, r1.getNombre());

		// Delete
		recetaRepository.delete(r1);
		recetas = recetaRepository.findAll();
		assertEquals(TAMANHO_INICIAL, recetas.size());
	}

}
