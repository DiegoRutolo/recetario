package eu.rutolo.recetario.recetas;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/ingrediente")
public class IngredienteController {
	private final Logger logger = LoggerFactory.getLogger(IngredienteController.class);
	
	@Autowired
	IngredienteService ingredienteService;

	@GetMapping
	public String listAll(Model model) {
		model.addAttribute("ingredientes", ingredienteService.findAllLazy());
		return "recetas/ingredientes";
	}

	@GetMapping("/{id}")
	public String get(@PathVariable("id") Long id, Model model) {
		model.addAttribute("ingrediente", ingredienteService.findById(id));
		return "recetas/ingredienteForm";
	}

	@GetMapping(value = "/foto/{id}", produces = MediaType.IMAGE_PNG_VALUE)
	public ResponseEntity<byte[]> getImage(@PathVariable("id") Long id) {
		byte[] img = ingredienteService.findById(id).getFoto();
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_PNG);
		return new ResponseEntity<>(img, headers, HttpStatus.OK);
	}

	@GetMapping("/new")
	public String newIngredienteGet(Ingrediente ingrediente, Model model) {
		return "recetas/ingredienteForm";
	}

	@PostMapping
	public String newIngredientePost(Ingrediente ingrediente, @RequestParam("archivoFoto") MultipartFile file,
			BindingResult result, Model model) throws IOException {
		logger.debug("Intentando guardar {}", ingrediente.toString());
		if (result.hasErrors()) {
			logger.debug(result.toString());
			return "recetas/ingredienteForm";
		}

		if (file != null && !file.isEmpty()) {
			ingrediente.setFoto(file.getBytes());
		}

		ingredienteService.save(ingrediente);
		return "redirect:/ingrediente";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id, Model model) {
		ingredienteService.delete(id);
		return "redirect:/ingrediente";
	}

}
