package eu.rutolo.recetario.home;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {
    private final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @GetMapping
    public String base(Model model) {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home(Model model) {
        return "main";
    }
}
