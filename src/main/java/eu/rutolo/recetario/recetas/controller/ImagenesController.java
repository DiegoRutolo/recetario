package eu.rutolo.recetario.recetas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import eu.rutolo.recetario.recetas.data.IngredienteService;
import eu.rutolo.recetario.recetas.data.RecetaService;

@Controller
@RequestMapping("/foto")
public class ImagenesController {

	@Autowired
	IngredienteService ingredienteService;

	@Autowired
	private RecetaService recetaService;

	@GetMapping(value = "/ingrediente/{id}", produces = MediaType.IMAGE_PNG_VALUE)
	public ResponseEntity<byte[]> getIngrediente(@PathVariable("id") Long id) {
		byte[] img = ingredienteService.findById(id).getFoto();
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_PNG);
		return new ResponseEntity<>(img, headers, HttpStatus.OK);
	}

	@GetMapping(value = "/receta/{id}", produces = MediaType.IMAGE_PNG_VALUE)
	public ResponseEntity<byte[]> getReceta(@PathVariable("id") Long id) {
		byte[] img = recetaService.findById(id).getFoto();
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_PNG);
		return new ResponseEntity<>(img, headers, HttpStatus.OK);
	}
	
}
