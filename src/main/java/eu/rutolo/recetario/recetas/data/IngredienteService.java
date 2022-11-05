package eu.rutolo.recetario.recetas.data;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.LazyContextVariable;

import eu.rutolo.recetario.recetas.model.Ingrediente;
import eu.rutolo.recetario.security.FilesService;

@Service
public class IngredienteService {
	private final Logger logger = LoggerFactory.getLogger(IngredienteService.class);

	@Autowired
	IngredienteRepository ingredienteRepository;

	@Autowired
	FilesService filesService;
	
	public List<Ingrediente> findAll() {
		return ingredienteRepository.findAll();
	}

	public LazyContextVariable<List<Ingrediente>> findAllLazy() {
		return new LazyContextVariable<List<Ingrediente>>() {
			@Override
			protected List<Ingrediente> loadValue() {
				return ingredienteRepository.findAll();
			}
		};
	}

	public Ingrediente findById(Long id) {
		return ingredienteRepository.findById(id)
			.orElseThrow(IllegalArgumentException::new);
	}

	public Ingrediente save(Ingrediente ingrediente) {
		// TODO: Comprobaciones sobre la imagen.
		Ingrediente i = ingredienteRepository.save(ingrediente);
		logger.info("Guardado Ingrediente {}", i.toString());
		return i;
	}

	public void delete(Long id) {
		delete(findById(id));
	}

	public void delete(Ingrediente ingrediente) {
		ingredienteRepository.delete(ingrediente);
		logger.info("Eliminado ingrediente {}", ingrediente.toString());
	}
}
