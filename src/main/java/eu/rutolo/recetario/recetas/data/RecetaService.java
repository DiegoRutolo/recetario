package eu.rutolo.recetario.recetas.data;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.LazyContextVariable;

import eu.rutolo.recetario.recetas.data.repo.RecetaIngredieteRepository;
import eu.rutolo.recetario.recetas.data.repo.RecetaRepository;
import eu.rutolo.recetario.recetas.model.Receta;
import eu.rutolo.recetario.recetas.model.RecetaIngrediente;
import eu.rutolo.recetario.security.users.Usuario;

@Service
public class RecetaService {

	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private RecetaRepository recetaRepository;

	@Autowired
	private RecetaIngredieteRepository recetaIngredieteRepository;

	@Autowired
	private IngredienteService ingredienteService;


	public List<Receta> findAll() {
		return recetaRepository.findAll();
	}

	public LazyContextVariable<List<Receta>> findAllLazy() {
		return new LazyContextVariable<List<Receta>>() {
			@Override
			protected List<Receta> loadValue() {
				return findAll();
			}
		};
	}
	
	public Receta findById(UUID id) {
		return recetaRepository.findById(id)
			.orElseThrow(IllegalArgumentException::new);
	}

	public List<Receta> findByCreador(Usuario creador) {
		return recetaRepository.findRecetasByCreador(creador);
	}

	public List<Receta> findByCreadorOrNull(Usuario creador) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Receta> cq = cb.createQuery(Receta.class);
		Root<Receta> root = cq.from(Receta.class);

		Predicate p = cb.or(
			cb.equal(root.get("creador"), creador),
			cb.isNull(root.get("creador"))
		);
		cq.where(p);
		return entityManager.createQuery(cq).getResultList();
	}

	public Receta findByRecetaIngredienteId(UUID recetaIngredienteId) {
		return recetaIngredieteRepository.findById(recetaIngredienteId).get().getReceta();
	}

	public RecetaIngrediente findRecetaIngrediente(UUID recetaIngredienteId) {
		return recetaIngredieteRepository.findById(recetaIngredienteId).get();
	}

	public Receta create(String nombre, Usuario creador) {
		Receta r = new Receta();
		r.setNombre(nombre);
		r.setCreador(creador);
		return save(r);
	}

	public Receta save(Receta r) {
		return recetaRepository.save(r);
	}

	public void delete(UUID id) {
		delete(findById(id));
	}
	
	public void delete(Receta r) {
		recetaRepository.delete(r);
	}

	public void saveIngrediente(Receta r, UUID ingredienteId, Float cantidad, String texto) {
		RecetaIngrediente ri = new RecetaIngrediente();
		ri.setReceta(r);
		if (ingredienteId != null) {
			ri.setIngrediente(ingredienteService.findById(ingredienteId));
		}
		ri.setCantidad(cantidad);
		ri.setTexto(texto);
		recetaIngredieteRepository.save(ri);
	}

	public List<RecetaIngrediente> findIngredientesByReceta(UUID recetaId) {
        return findIngredientesByReceta(findById(recetaId));
    }

	public List<RecetaIngrediente> findIngredientesByReceta(Receta r) {
		return recetaIngredieteRepository.findByReceta(r);
	}

	/**
	 * Elimina el ingrediente de la receta
	 */
	public void removeIngrediente(UUID recetaIngredienteId) {
		recetaIngredieteRepository.deleteById(recetaIngredienteId);
	}

	/**
	 * Guarda el paso indicado
	 */
	public void savePaso(UUID recetaId, Integer numeroPaso, String descripcion) {
		Receta r = findById(recetaId);
		r.getPasos().put(numeroPaso, descripcion);
		save(r);
	}

	public void deletePaso(UUID recetaId, Integer numeroPaso) {
		Receta receta = findById(recetaId);
		receta.getPasos().remove(numeroPaso);
		save(receta);
	}

}
