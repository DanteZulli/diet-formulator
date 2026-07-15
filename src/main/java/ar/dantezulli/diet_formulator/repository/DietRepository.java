package ar.dantezulli.diet_formulator.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.dantezulli.diet_formulator.model.Diet;

/**
 * Repository for Diet entity.
 * Repositorio para la entidad Diet.
 */
@Repository
public interface DietRepository extends JpaRepository<Diet, Long> {

    /**
     * Finds all diets for a given animal profile.
     * Busca todas las dietas para un perfil de animal dado.
     */
    List<Diet> findByAnimalProfileId(Long animalProfileId);
}
