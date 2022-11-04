package eu.rutolo.recetario.home;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import eu.rutolo.recetario.recetas.RecetaService;
import eu.rutolo.recetario.security.users.UserDetailsServiceImpl;

@Controller
public class HomeController {
    // private final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    RecetaService recetaService;

    @Autowired
	UserDetailsServiceImpl userDetailsServiceImpl;

    @GetMapping("/")
    public String base(Model model) {
        model.addAttribute("recetas", recetaService.findAll());
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
