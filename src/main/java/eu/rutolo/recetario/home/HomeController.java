package eu.rutolo.recetario.home;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.context.LazyContextVariable;

import eu.rutolo.recetario.recetas.Receta;
import eu.rutolo.recetario.recetas.RecetaRepository;
import eu.rutolo.recetario.security.users.UserDetailsServiceImpl;

@Controller
public class HomeController {
    // private final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    RecetaRepository recetaRepository;

    @Autowired
	UserDetailsServiceImpl userDetailsServiceImpl;

    @GetMapping("/")
    public String base(Model model) {
        model.addAttribute(
            "recetas",
            new LazyContextVariable<List<Receta>>() {
                @Override
                protected List<Receta> loadValue() {
                    return recetaRepository.findAll();
                }
            });
        return "index";
    }

    @GetMapping("/perfil")
    public String perfil(Model model) {
        return "user/perfil";
    }

    @PostMapping("/perfil/changePasswd")
    public String changePasswordPost(@RequestParam("password") String password, Model model, HttpServletRequest req) {
        userDetailsServiceImpl.changePassword(req.getRemoteUser(), password);
        return "redirect:/";
    }
}
