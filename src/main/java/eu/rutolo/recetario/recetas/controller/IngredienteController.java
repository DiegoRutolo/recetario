package eu.rutolo.recetario.recetas.controller;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import eu.rutolo.recetario.recetas.data.IngredienteService;
import eu.rutolo.recetario.recetas.model.Ingrediente;
import eu.rutolo.recetario.recetas.model.IngredienteUsuario;
import eu.rutolo.recetario.security.users.Usuario;

@Controller
@RequestMapping("/ingrediente")
public class IngredienteController {
	private final Logger logger = LoggerFactory.getLogger(IngredienteController.class);
	
	@Autowired
	UserDetailsService uDetailsService;

	@Autowired
	IngredienteService ingredienteService;

	@GetMapping
	public String listAll(Model model) {
		model.addAttribute("ingredientes", ingredienteService.findAll());
		return "recetas/ingredientes";
	}

	@GetMapping("/{id}")
	public String get(@PathVariable("id") UUID id, Model model) {
		model.addAttribute("ingrediente", ingredienteService.findById(id));
		return "recetas/ingredienteForm";
	}

	@GetMapping("/new")
	public String newIngredienteGet(Ingrediente ingrediente, Model model) {
		return "recetas/ingredienteForm";
	}

	@PostMapping
	public String newIngredientePost(IngredienteUsuario ingrediente, @RequestParam("archivoFoto") MultipartFile file,
			BindingResult result, Model model, HttpServletRequest request) throws IOException {
		logger.debug("Intentando guardar {}", ingrediente.toString());
		if (result.hasErrors()) {
			logger.debug(result.toString());
			return "recetas/ingredienteForm";
		}

		if (file != null && !file.isEmpty()) {
			ingrediente.setFoto(file.getBytes());
		}

		ingrediente.setUsuario((Usuario) uDetailsService.loadUserByUsername(request.getUserPrincipal().getName()));

		ingredienteService.save(ingrediente);
		return "redirect:/ingrediente";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") UUID id, Model model) {
		ingredienteService.delete(id);
		return "redirect:/ingrediente";
	}

}
