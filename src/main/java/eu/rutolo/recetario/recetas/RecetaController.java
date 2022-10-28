package eu.rutolo.recetario.recetas;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.context.LazyContextVariable;

@Controller
@RequestMapping("/receta")
public class RecetaController {

    private final Logger logger = LoggerFactory.getLogger(RecetaController.class);

    @Autowired
    private RecetaRepository recetaRepository;
    
    @GetMapping
    public String verRecetas(Model model) {
        model.addAttribute(
            "recetas",
            new LazyContextVariable<List<Receta>>() {
                @Override
                protected List<Receta> loadValue() {
                    return recetaRepository.findAll();
                }
            });
        return "recetas/recetasMain";
    }
}
