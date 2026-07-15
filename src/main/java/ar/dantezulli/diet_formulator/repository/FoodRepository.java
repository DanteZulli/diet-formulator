package ar.dantezulli.diet_formulator.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.dantezulli.diet_formulator.model.Food;
import ar.dantezulli.diet_formulator.model.enums.TipoAlimento;

/**
 * Repository for Food entity.
 * Repositorio para la entidad Food.
 */
@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {

    /**
     * Finds foods by type. / Busca alimentos por tipo.
     */
    List<Food> findByTipo(TipoAlimento tipo);

    /**
     * Finds foods by name (case-insensitive contains). / Busca alimentos por nombre (contiene, sin importar mayúsculas).
     */
    List<Food> findByNombreContainingIgnoreCase(String nombre);
}
