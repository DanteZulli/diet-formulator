package ar.dantezulli.diet_formulator.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import ar.dantezulli.diet_formulator.model.entities.Food;
import ar.dantezulli.diet_formulator.model.enums.FoodType;
import ar.dantezulli.diet_formulator.model.enums.PortionUnit;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class FoodServiceTest {

    @Autowired
    private FoodService service;

    @Test
    void findAllReturnsEmptyListWhenNoFoods() {
        List<Food> result = service.findAll();
        assertTrue(result.isEmpty());
    }

    @Test
    void saveAndFindById() {
        Food saved = service.save(buildValidFood("Chicken Breast"));

        assertNotNull(saved.getId());
        assertEquals("Chicken Breast", saved.getName());

        Optional<Food> found = service.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("Chicken Breast", found.get().getName());
    }

    @Test
    void findAllReturnsSavedFoods() {
        service.save(buildValidFood("Chicken"));
        service.save(buildValidFood("Rice"));

        List<Food> result = service.findAll();
        assertEquals(2, result.size());
    }

    @Test
    void findByIdReturnsEmptyForNonexistentId() {
        Optional<Food> result = service.findById(UUID.randomUUID());
        assertFalse(result.isPresent());
    }

    @Test
    void deleteByIdRemovesFood() {
        Food saved = service.save(buildValidFood("Chicken"));
        service.deleteById(saved.getId());

        Optional<Food> found = service.findById(saved.getId());
        assertFalse(found.isPresent());
    }

    @Test
    void searchFindsByNameCaseInsensitive() {
        service.save(buildValidFood("Chicken Breast"));
        service.save(buildValidFood("Rice"));
        service.save(buildValidFood("Chicken Liver"));

        List<Food> results = service.search("chicken");
        assertEquals(2, results.size());
        assertTrue(results.stream().allMatch(f ->
            f.getName().toLowerCase().contains("chicken")));
    }

    @Test
    void searchReturnsEmptyForNoMatch() {
        service.save(buildValidFood("Chicken"));

        List<Food> results = service.search("xyz");
        assertTrue(results.isEmpty());
    }

    @Test
    void savePersistsNutrients() {
        Food food = buildValidFood("Chicken");
        food.getNutrients().put(
            ar.dantezulli.diet_formulator.model.enums.Nutrient.PROTEIN_G, 31.0);
        food.getNutrients().put(
            ar.dantezulli.diet_formulator.model.enums.Nutrient.TOTAL_LIPIDS_G, 3.6);

        Food saved = service.save(food);

        Optional<Food> found = service.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals(31.0, found.get().getNutrients().get(
            ar.dantezulli.diet_formulator.model.enums.Nutrient.PROTEIN_G));
        assertEquals(3.6, found.get().getNutrients().get(
            ar.dantezulli.diet_formulator.model.enums.Nutrient.TOTAL_LIPIDS_G));
    }

    @Test
    void savePersistsFoodTypeAndPortion() {
        Food food = buildValidFood("Supplement");
        food.setType(FoodType.SUPPLEMENT);
        food.setPortion(500.0);
        food.setPortionUnit(PortionUnit.CAPSULES);

        Food saved = service.save(food);

        Optional<Food> found = service.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals(FoodType.SUPPLEMENT, found.get().getType());
        assertEquals(500.0, found.get().getPortion());
        assertEquals(PortionUnit.CAPSULES, found.get().getPortionUnit());
    }

    private Food buildValidFood(String name) {
        Food food = new Food();
        food.setName(name);
        food.setType(FoodType.ANIMAL);
        food.setPortion(100.0);
        food.setPortionUnit(PortionUnit.GRAMS);
        return food;
    }


}
