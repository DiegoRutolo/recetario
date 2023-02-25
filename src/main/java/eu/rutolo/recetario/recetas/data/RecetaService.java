package eu.rutolo.recetario.recetas.data;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.LazyContextVariable;

import eu.rutolo.recetario.recetas.model.Ingrediente;
import eu.rutolo.recetario.recetas.model.Paso;
import eu.rutolo.recetario.recetas.model.Receta;
import eu.rutolo.recetario.recetas.model.RecetaIngrediente;
import eu.rutolo.recetario.recetas.model.RecetaIngredienteId;
import eu.rutolo.recetario.recetas.model.RecetaPasoId;
import eu.rutolo.recetario.security.users.Usuario;

@Service
public class RecetaService {

	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private RecetaRepository recetaRepository;

	@Autowired
	private RecetaIngredieteRepository recetaIngredieteRepository;

	@Autowired PasoRepository pasoRepository;


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

	public void saveIngrediente(Receta r, Ingrediente i, Float cantidad) {
		RecetaIngrediente ri = new RecetaIngrediente();
		ri.setId(new RecetaIngredienteId(r.getId(), i.getId()));
		ri.setReceta(r);
		ri.setIngrediente(i);
		ri.setCantidad(cantidad);
		recetaIngredieteRepository.save(ri);
	}

	@Transactional
	public void saveIngredientes(Receta r, Map<Ingrediente, Float> ingredientes) {
		ingredientes.forEach((k, v) -> saveIngrediente(r, k, v));
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
	public void removeIngrediente(UUID recetaId, UUID ingredienteId) {
		recetaIngredieteRepository.deleteById(new RecetaIngredienteId(recetaId, ingredienteId));
	}

	public List<Paso> findPasos(UUID recetaId) {
		return pasoRepository.findByRecetaId(recetaId);
	}

	/**
	 * Guarda la descripción como siguiente paso
	 */
	public void saveNewPaso(UUID recetaId, String descripcion) {
		savePaso(recetaId, pasoRepository.findLastPaso(recetaId) + 1, descripcion);
	}

	/**
	 * Guarda el paso indicado
	 */
	public void savePaso(UUID recetaId, Integer numeroPaso, String descripcion) {
		Receta r = findById(recetaId);
		
		Paso paso = new Paso();
		paso.setId(new RecetaPasoId(recetaId, numeroPaso));
		paso.setDescripcion(descripcion);
		paso.setReceta(r);

		// Como Receta es la propietaria de la relación, hay que añadir el paso y guardar la receta
		r.getPasos().add(paso);
		save(r);
	}

	public void deletePaso(UUID recetaId, Integer numeroPaso) {
		Receta receta = findById(recetaId);
		Paso paso = pasoRepository.findById(new RecetaPasoId(recetaId, numeroPaso)).get();
		
		// Eliminar el paso de la lista
		receta.getPasos().remove(paso);
	
		// Actualizar numeros del resto
		for (Paso p : receta.getPasos()) {
			if (p.getId().getNumero().compareTo(numeroPaso) > 0) {
				p.getId().setNumero(p.getId().getNumero()-1);
			}
		}

		// Guardar receta y borrar en bd
		save(receta);
		pasoRepository.delete(paso);
	}

}
