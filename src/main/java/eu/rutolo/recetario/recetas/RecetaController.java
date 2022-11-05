package eu.rutolo.recetario.recetas;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

import eu.rutolo.recetario.security.users.Usuario;

@Controller
@RequestMapping("/receta")
public class RecetaController {
    private final Logger logger = LoggerFactory.getLogger(RecetaController.class);

    @Autowired
    private RecetaService recetaService;
    
    @GetMapping
    public String listAll(Model model, @AuthenticationPrincipal Usuario usuario) {
        model.addAttribute("recetas", recetaService.findByCreador(usuario));
        return "recetas/recetasMain";
    }

    @GetMapping("/{id}")
	public String get(@PathVariable("id") Long id, Model model) {
        model.addAttribute("receta", recetaService.findById(id));
		return "recetas/recetaForm";
	}

    @GetMapping(value = "/foto/{id}", produces = MediaType.IMAGE_PNG_VALUE)
	public ResponseEntity<byte[]> getImage(@PathVariable("id") Long id) {
		byte[] img = recetaService.findById(id).getFoto();
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_PNG);
		return new ResponseEntity<>(img, headers, HttpStatus.OK);
	}

    @GetMapping("/new")
	public String newRecetaGet(Receta receta, Model model) {
		return "recetas/recetaForm";
	}

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
	public String delete(@PathVariable("id") Long id, Model model, @AuthenticationPrincipal Usuario usuario) {

        try {
            Receta r = recetaService.findById(id);
            if (usuario.equals(r.getCreador()) || usuario.isRolAdmin()) {
                recetaService.delete(r);
            } else {
                logger.info(usuario.getUsername() + " intenta borrar receta " + id + " sin permisos");
            }
        } catch (Exception e) {
            logger.error("Error borrando receta " + id, e);
        }

		return "redirect:/receta";
	}
}
