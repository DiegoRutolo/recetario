package eu.rutolo.recetario.security.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/user")
public class UsuarioController {

	@Autowired
	UserDetailsServiceImpl userDetailsServiceImpl;

	@Autowired
	PasswordEncoder passwordEncoder;

	@GetMapping
	public String list(Model model) {
		model.addAttribute("usuarios", userDetailsServiceImpl.findAll());
		return "user/usersMain";
	}

	@GetMapping("/{username}")
	public String get(@PathVariable("username") String username, Model model) {
		model.addAttribute("user", (Usuario) userDetailsServiceImpl.loadUserByUsername(username));
		return "user/userForm";
	}

	@GetMapping("/new")
	public String newUserGet(Model model) {
		model.addAttribute("user", new Usuario());
		return "user/userForm";
	}

	@PostMapping
	public String saveUserPost(Usuario usuario, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "user/userForm";
		}
		usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
		userDetailsServiceImpl.save(usuario);
		return "redirect:/admin/user";
	}
	
}
