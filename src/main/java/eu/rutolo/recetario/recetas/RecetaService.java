package eu.rutolo.recetario.recetas;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.LazyContextVariable;

import eu.rutolo.recetario.security.users.Usuario;

@Service
public class RecetaService {
	
	@Autowired
	private RecetaRepository recetaRepository;

	@Autowired
	private EntityManager entityManager;

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

	public Receta findById(Long id) {
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

	public void delete(Long id) {
		delete(findById(id));
	}
	
	public void delete(Receta r) {
		recetaRepository.delete(r);
	}
}
