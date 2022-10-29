package eu.rutolo.recetario.recetas;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.LazyContextVariable;

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
		return ingredienteRepository.save(ingrediente);
	}

	public Ingrediente save(Ingrediente ingrediente, MultipartFile foto) {
		String path = "files/fotos/ingredientes";
		String filename = String.format("foto-%d.%s", ingrediente.getId(),
				FilenameUtils.getExtension(foto.getOriginalFilename()));
		try {
			Path rutaArchivoGuardado = filesService.saveFile(path, filename, foto);
			ingrediente.setFoto(rutaArchivoGuardado.toString());
			return ingredienteRepository.save(ingrediente);
		} catch (IOException e) {
			logger.error("Error creando ingrediente con archivo", e);
			//FIXME: Esto no está bien, debería avisar de alguna otra forma
			return null;
		}
	}

	public void delete(Long id) {
		delete(findById(id));
	}

	public void delete(Ingrediente ingrediente) {
		ingredienteRepository.delete(ingrediente);
	}
}
