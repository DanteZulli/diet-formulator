package ar.dantezulli.diet_formulator.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.dantezulli.diet_formulator.model.entities.AnimalProfile;

/**
 * Repository for AnimalProfile entity.
 */
public interface AnimalProfileRepository extends JpaRepository<AnimalProfile, UUID> {
}
