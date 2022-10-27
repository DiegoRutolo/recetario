package eu.rutolo.recetario.home;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.context.LazyContextVariable;

import eu.rutolo.recetario.recetas.Receta;
import eu.rutolo.recetario.recetas.RecetaRepository;

@Controller
@RequestMapping("/")
public class HomeController {
    // private final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    RecetaRepository recetaRepository;

    @GetMapping
    public String base(Model model) {
        model.addAttribute(
            "recetas",
            new LazyContextVariable<List<Receta>>() {
                @Override
                protected List<Receta> loadValue() {
                    return recetaRepository.findAll();
                }
            });
        return "main";
    }

    @GetMapping("/home")
    public String home(Model model) {
        return "private/perfil";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "public/login";
    }

    @GetMapping("/perfil")
    @PreAuthorize("hasRole('ROL_USER')")
    public String perfil(Model model) {
        return "private/perfil";
    }
}
