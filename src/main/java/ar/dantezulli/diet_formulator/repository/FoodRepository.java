package ar.dantezulli.diet_formulator.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.dantezulli.diet_formulator.model.Food;
import ar.dantezulli.diet_formulator.model.enums.FoodType;

public interface FoodRepository extends JpaRepository<Food, Long> {

    List<Food> findByType(FoodType type);

    List<Food> findByNameContainingIgnoreCase(String name);
}
