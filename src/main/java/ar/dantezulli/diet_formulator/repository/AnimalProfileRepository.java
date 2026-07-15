package ar.dantezulli.diet_formulator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.dantezulli.diet_formulator.model.AnimalProfile;

/**
 * Repository for AnimalProfile entity.
 * Repositorio para la entidad AnimalProfile.
 */
@Repository
public interface AnimalProfileRepository extends JpaRepository<AnimalProfile, Long> {
}
