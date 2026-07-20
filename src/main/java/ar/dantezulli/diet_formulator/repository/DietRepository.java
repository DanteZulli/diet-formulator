package ar.dantezulli.diet_formulator.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.dantezulli.diet_formulator.model.entities.Diet;

public interface DietRepository extends JpaRepository<Diet, UUID> {
}
