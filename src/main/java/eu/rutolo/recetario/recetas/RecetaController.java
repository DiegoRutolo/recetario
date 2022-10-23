package eu.rutolo.recetario.recetas;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/receta")
public class RecetaController {

    private final Logger logger = LoggerFactory.getLogger(RecetaController.class);

    @Autowired
    private RecetaRepository recetaRepository;
    
    @GetMapping
    public @ResponseBody ResponseEntity<List<Receta>> list() {
        List<Receta> recetas = recetaRepository.findAll();
        logger.info(recetas.toString());
        return new ResponseEntity<List<Receta>>(recetas, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Receta> post(@RequestBody Receta receta) {
        Receta result = recetaRepository.save(receta);
        return new ResponseEntity<Receta>(result, HttpStatus.OK);
    }

    @GetMapping("/ver")
    public String verRecetas(Model model) {
        model.addAttribute("recetas", recetaRepository.findAll());
        return "main";
    }
}
