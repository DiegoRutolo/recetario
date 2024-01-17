package eu.rutolo.recetario.recetas.data;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.LazyContextVariable;

import eu.rutolo.recetario.recetas.data.repo.IngredienteRepository;
import eu.rutolo.recetario.recetas.data.repo.IngredienteUsuarioRepository;
import eu.rutolo.recetario.recetas.model.Ingrediente;
import eu.rutolo.recetario.recetas.model.IngredienteUsuario;
import eu.rutolo.recetario.security.FilesService;
import eu.rutolo.recetario.security.users.Usuario;

@Service
public class IngredienteService {
	private final Logger logger = LoggerFactory.getLogger(IngredienteService.class);

	@Autowired
	UserDetailsService uDetailsService;

	@Autowired
	IngredienteRepository ingredienteRepository;

	@Autowired
	IngredienteUsuarioRepository ingredienteUsuarioRepository;

	@Autowired
	FilesService filesService;

	/**
	 * Devuleve todos los ingredientes públicos.
	 * 
	 * Si es admin, devuelve también los de todos los usuarios.
	 * 
	 * Si es usuario, devuelve también los propios.
	 */
	public List<Ingrediente> findByUser(HttpServletRequest request) {
		Usuario usuario = (Usuario) uDetailsService.loadUserByUsername(request.getUserPrincipal().getName());
		return findByUser(usuario);
	}

	/**
	 * Devuleve todos los ingredientes públicos.
	 * 
	 * Si es admin, devuelve también los de todos los usuarios.
	 * 
	 * Si es usuario, devuelve también los propios.
	 */
	public List<Ingrediente> findByUser(Usuario usuario) {
		// TODO: Buscar forma más eficiente
		List<Ingrediente> ingredientes = ingredienteRepository.findAll().stream()
			.filter(i -> !(i instanceof IngredienteUsuario))
			.collect(Collectors.toList());

		if (usuario.isRolAdmin()) {
			ingredientes.addAll(ingredienteUsuarioRepository.findAll());
		} else {
			ingredientes.addAll(ingredienteUsuarioRepository.findByUsuario(usuario));
		}

		return ingredientes;
	}
	
	public List<Ingrediente> findAll() {
		return ingredienteRepository.findAll();
	}

	public LazyContextVariable<List<Ingrediente>> findAllLazy() {
		return new LazyContextVariable<List<Ingrediente>>() {
			@Override
			protected List<Ingrediente> loadValue() {
				return ingredienteRepository.findAll();
			}
		};
	}

	public Ingrediente findById(UUID id) {
		if (id == null) {
			return null;
		}

		return ingredienteRepository.findById(id)
			.orElseThrow(IllegalArgumentException::new);
	}

	public Ingrediente save(Ingrediente ingrediente) {
		if (ingrediente == null) {
			return null;
		}

		// TODO: Comprobaciones sobre la imagen.
		Ingrediente i = ingredienteRepository.save(ingrediente);
		logger.info("Guardado Ingrediente {}", i.toString());
		return i;
	}

	public void delete(UUID id) {
		delete(findById(id));
	}

	public void delete(Ingrediente ingrediente) {
		if (ingrediente == null) {
			return;
		}

		ingredienteRepository.delete(ingrediente);
		logger.info("Eliminado ingrediente {}", ingrediente.toString());
	}

	public boolean isEquivalente(Ingrediente ing1, Ingrediente ing2) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'isEquivalente'");
	}
}
