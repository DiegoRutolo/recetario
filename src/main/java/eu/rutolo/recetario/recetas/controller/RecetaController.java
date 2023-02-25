package eu.rutolo.recetario.recetas.controller;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import eu.rutolo.recetario.recetas.data.RecetaService;
import eu.rutolo.recetario.recetas.model.Receta;
import eu.rutolo.recetario.security.users.Usuario;

@Controller
@RequestMapping("/receta")
public class RecetaController {
	private final Logger logger = LoggerFactory.getLogger(RecetaController.class);

	@Autowired
	private RecetaService recetaService;

	@Autowired
	private IngredienteService ingredienteService;

	@GetMapping
	public String listAll(Model model, @AuthenticationPrincipal Usuario usuario) {
		model.addAttribute("recetas", recetaService.findByCreador(usuario));
		return "recetas/recetasMain";
	}

	/**
	 * Devuelve la vista no editable de una receta
	 */
	@GetMapping("/{id}")
	public String view(@PathVariable("id") UUID id, Model model) {
		model.addAttribute("receta", recetaService.findById(id));
		model.addAttribute("ingredientesReceta", recetaService.findIngredientesByReceta(id));
		return "recetas/recetaView";
	}

	/**
	 * Devuelve el formulario para editar una receta
	 */
	@GetMapping("/{id}/edit")
	public String get(@PathVariable("id") UUID id, Model model) {
		model.addAttribute("receta", recetaService.findById(id));
		model.addAttribute("ingredientesReceta", recetaService.findIngredientesByReceta(id));
		return "recetas/recetaForm";
	}

	/**
	 * Devuelve el formulario para crear una receta.
	 */
	@GetMapping("/new")
	public String newRecetaGet(Receta receta, Model model) {
		model.addAttribute("ingredientes", ingredienteService.findAll());
		return "recetas/recetaForm";
	}

	/**
	 * Recibe los datos para crear una receta nueva.
	 */
	@PostMapping
	public String newRecetaPost(Receta receta, @RequestParam("archivoFoto") MultipartFile file,
			BindingResult result, Model model, @AuthenticationPrincipal Usuario usuario) throws IOException {
		if (result.hasErrors()) {
			logger.debug(result.toString());
			return "recetas/recetaForm";
		}

		if (file != null && !file.isEmpty()) {
			receta.setFoto(file.getBytes());
		}
		receta.setCreador(usuario);
		recetaService.save(receta);
		return "redirect:/receta";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") UUID id, Model model, @AuthenticationPrincipal Usuario usuario) {

		try {
			Receta r = recetaService.findById(id);
			if (usuario.equals(r.getCreador()) || usuario.isRolAdmin()) {
				recetaService.delete(r);
			} else {
				logger.info("{} intenta borrar receta {} sin permisos", usuario.getUsername(), r.getId().toString());
			}
		} catch (Exception e) {
			logger.error("Error borrando receta " + id, e);
		}

		return "redirect:/receta";
	}

	/**
	 * Formulario con la lista de ingredientes y botones para editarlos.
	 */
	@GetMapping("/{id}/ingredientes")
	public String recetaIngredientesGet(@PathVariable("id") UUID id, Model model) {
		model.addAttribute("ingredientes", ingredienteService.findAll());
		Receta r = recetaService.findById(id);
		model.addAttribute("receta", r);
		model.addAttribute("ingredientesReceta", recetaService.findIngredientesByReceta(r));
		return "recetas/recetaIngredientesForm";
	}

	/**
	 * Recibe los datos para añadir un ingrediente a una receta
	 */
	@PostMapping("/ingrediente/add")
	public String recetaIngredienteAddPost(@RequestParam("recetaId") UUID recetaId,
			@RequestParam("ingredienteId") UUID ingredienteId, @RequestParam("cantidad") Float cantidad) {
		
		logger.debug("Añadir {} del ingrediente {} a la receta {}", cantidad, ingredienteId, recetaId);
		recetaService.saveIngrediente(recetaService.findById(recetaId), ingredienteService.findById(ingredienteId), cantidad);
		return "redirect:/receta/" + recetaId + "/ingredientes";
	}

	/**
	 * Elimina el ingrediente de la receta
	 */
	@PostMapping("/ingrediente/delete")
	public String recetaIngredienteDeletePost(@RequestParam("recetaId") UUID recetaId,
	@RequestParam("ingredienteId") UUID ingredienteId) {

		logger.debug("Quitar el ingrediente {} de la receta {}", ingredienteId, recetaId);
		recetaService.removeIngrediente(recetaId, ingredienteId);
		return "redirect:/receta/" + recetaId + "/ingredientes";
	}

	/**
	 * Formulario para editar los pasos de la receta.
	 */
	@GetMapping("/{recetaId}/pasos")
	public String recetaPasosGet(@PathVariable("recetaId") UUID recetaId, Model model) {
		Receta r = recetaService.findById(recetaId);
		model.addAttribute("receta", r);
		model.addAttribute("pasosReceta", recetaService.findPasos(recetaId));
		return "recetas/recetaPasosForm";
	}

	/**
	 * Recibe los datos para añadir o editar un paso. Si el numero indicado ya existe lo sobrescribe, si no lo añade.
	 */
	@PostMapping("/paso/save")
	public String recetaPasoSavePost(@RequestParam("recetaId") UUID recetaId,
			@RequestParam("numero") Integer numero, @RequestParam("descripcion") String descripcion) {
		
		logger.debug("Guardar paso {} a la receta {}", numero, recetaId);
		recetaService.savePaso(recetaId, numero, descripcion);
		return "redirect:/receta/" + recetaId + "/pasos";
	}

	/**
	 * Elimina el ingrediente de la receta
	 */
	@PostMapping("/paso/delete")
	public String recetaPasoDeletePost(@RequestParam("recetaId") UUID recetaId,
			@RequestParam("numero") Integer numero) {

		logger.debug("Eliminar paso {} de la receta {}", numero, recetaId);
		recetaService.deletePaso(recetaId, numero);
		return "redirect:/receta/" + recetaId + "/pasos";
	}

}
