package ar.dantezulli.diet_formulator.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.dantezulli.diet_formulator.model.entities.Diet;

/**
 * Repository for Diet entity.
 */
public interface DietRepository extends JpaRepository<Diet, UUID> {

    /** Finds all diets for a given animal profile. */
    List<Diet> findByAnimalProfileId(UUID animalProfileId);
}
