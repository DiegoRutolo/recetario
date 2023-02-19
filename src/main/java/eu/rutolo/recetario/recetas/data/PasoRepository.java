package eu.rutolo.recetario.recetas.data;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import eu.rutolo.recetario.recetas.model.Paso;
import eu.rutolo.recetario.recetas.model.RecetaPasoId;

public interface PasoRepository extends JpaRepository<Paso, RecetaPasoId> {
    
    @Query("SELECT max(p.id.numero) "
        + "FROM Paso p " 
        + "WHERE p.id.recetaId = :recetaId")
    Integer findLastPaso(@Param("recetaId") UUID recetaId);

    @Query("SELECT p "
        + "FROM Paso p "
        + "WHERE p.id.recetaId = :recetaId "
        + "ORDER BY p.id.numero")
    List<Paso> findByRecetaId(@Param("recetaId") UUID recetaId);
}
