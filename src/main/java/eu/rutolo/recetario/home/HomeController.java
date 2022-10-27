package eu.rutolo.recetario.home;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {
    // private final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @GetMapping
    public String base(Model model) {
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
