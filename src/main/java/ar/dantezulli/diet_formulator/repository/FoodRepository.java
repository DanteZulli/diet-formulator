package ar.dantezulli.diet_formulator.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.dantezulli.diet_formulator.model.Food;
import ar.dantezulli.diet_formulator.model.enums.TipoAlimento;

/**
 * Repository for Food entity.
 */
public interface FoodRepository extends JpaRepository<Food, Long> {

    /** Finds foods by type. */
    List<Food> findByTipo(TipoAlimento tipo);

    /** Finds foods by name (case-insensitive contains). */
    List<Food> findByNombreContainingIgnoreCase(String nombre);
}
