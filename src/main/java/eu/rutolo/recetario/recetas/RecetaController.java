package eu.rutolo.recetario.recetas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/receta")
public class RecetaController {

    private final Logger logger = LoggerFactory.getLogger(RecetaController.class);

    @Autowired
    private RecetaRepository recetaRepository;
    
    @GetMapping("/ver")
    public String verRecetas(Model model) {
        model.addAttribute("recetas", recetaRepository.findAll());
        return "main";
    }
}
