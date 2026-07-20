package ar.dantezulli.diet_formulator.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.dantezulli.diet_formulator.model.entities.Food;

public interface FoodRepository extends JpaRepository<Food, UUID> {

    List<Food> findByNameContainingIgnoreCase(String name);
}
