package eu.rutolo.recetario.recetas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.context.LazyContextVariable;

@Controller
@RequestMapping("/ingrediente")
public class IngredienteController {
	
	@Autowired
	IngredienteRepository ingredienteRepository;

	@GetMapping
	public String listAll(Model model) {
		model.addAttribute(
            "ingredientes",
            new LazyContextVariable<List<Ingrediente>>() {
                @Override
                protected List<Ingrediente> loadValue() {
                    return ingredienteRepository.findAll();
                }
            });
		return "recetas/ingredientes";
	}

	@GetMapping("/{id}")
	public String get(@PathVariable("id") Long id, Model model) {
		Ingrediente ingrediente = ingredienteRepository.findById(id)
			.orElseThrow(IllegalArgumentException::new);
		model.addAttribute("ingrediente", ingrediente);
		return "recetas/ingredienteForm";
	}

	@GetMapping("/new")
	public String nuevoIngredienteGet(Ingrediente ingrediente, Model model) {
		return "recetas/ingredienteForm";
	}

	@PostMapping
	public String nuevoIngredientePost(Ingrediente ingrediente, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "recetas/ingredienteForm";
		}

		ingredienteRepository.save(ingrediente);
		return "redirect:/ingrediente";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id, Model model) {
		Ingrediente ingrediente = ingredienteRepository.findById(id)
			.orElseThrow(IllegalArgumentException::new);
		ingredienteRepository.delete(ingrediente);
		return "redirect:/ingrediente";
	}

}
