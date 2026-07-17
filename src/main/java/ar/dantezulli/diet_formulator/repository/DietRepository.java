package ar.dantezulli.diet_formulator.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.dantezulli.diet_formulator.model.Diet;

/**
 * Repository for Diet entity.
 */
public interface DietRepository extends JpaRepository<Diet, Long> {

    /** Finds all diets for a given animal profile. */
    List<Diet> findByAnimalProfileId(Long animalProfileId);
}
