package eu.rutolo.recetario.recetas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import eu.rutolo.recetario.security.users.Usuario;

@Controller
@RequestMapping("/receta")
public class RecetaController {
    // private final Logger logger = LoggerFactory.getLogger(RecetaController.class);

    @Autowired
    private RecetaService recetaService;
    
    @GetMapping
    public String verRecetas(Model model, @AuthenticationPrincipal Usuario usuario) {
        model.addAttribute("recetas", recetaService.findByCreador(usuario));
        return "recetas/recetasMain";
    }
}
