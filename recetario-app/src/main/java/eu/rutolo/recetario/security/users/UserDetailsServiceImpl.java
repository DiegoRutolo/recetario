package eu.rutolo.recetario.security.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return usuarioRepository
			.findById(username)
			.orElseThrow(() -> new UsernameNotFoundException("No existe usuario " + username));
		
	}

	public List<Usuario> findAll() {
		return usuarioRepository.findAll();
	}

	public Usuario save(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}

	public Usuario changePassword(String username, String newPassword) {
		Usuario usuario = usuarioRepository
			.findById(username)
			.orElseThrow(() -> new UsernameNotFoundException("No existe usuario " + username));
		usuario.setPassword(passwordEncoder.encode(newPassword));
		usuario = usuarioRepository.save(usuario);
		return usuario;
	}
	
}
