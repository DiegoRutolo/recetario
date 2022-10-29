package eu.rutolo.recetario.recetas;

import org.springframework.beans.factory.annotation.Autowired;
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

	@GetMapping("/new")
	public String nuevoIngredienteGet(Ingrediente ingrediente, Model model) {
		return "recetas/ingredienteForm";
	}

	@PostMapping
	public String nuevoIngredientePost(Ingrediente ingrediente, @RequestParam("archivoFoto") MultipartFile file,
			BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "recetas/ingredienteForm";
		}

		ingredienteService.save(ingrediente, file);
		return "redirect:/ingrediente";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id, Model model) {
		ingredienteService.delete(id);
		return "redirect:/ingrediente";
	}

}
