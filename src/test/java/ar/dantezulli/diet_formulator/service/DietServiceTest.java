package ar.dantezulli.diet_formulator.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;

import ar.dantezulli.diet_formulator.model.entities.AnimalProfile;
import ar.dantezulli.diet_formulator.model.entities.Diet;
import ar.dantezulli.diet_formulator.model.entities.DietItem;
import ar.dantezulli.diet_formulator.model.entities.Food;
import ar.dantezulli.diet_formulator.model.enums.ActivityLevel;
import ar.dantezulli.diet_formulator.model.enums.FoodType;
import ar.dantezulli.diet_formulator.model.enums.LifeStage;
import ar.dantezulli.diet_formulator.model.enums.Nutrient;
import ar.dantezulli.diet_formulator.model.enums.QuantityUnit;
import ar.dantezulli.diet_formulator.model.enums.Species;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class DietServiceTest {

    @Autowired
    private DietService dietService;

    @Autowired
    private AnimalProfileService profileService;

    @Autowired
    private FoodService foodService;

    @Autowired
    private jakarta.persistence.EntityManager entityManager;

    private AnimalProfile testProfile;
    private Food testFood;

    @BeforeEach
    void setUp() {
        testProfile = profileService.save(buildValidProfile("Otto", Species.DOG));
        testFood = foodService.save(buildValidFood("Chicken Breast"));
    }

    @Test
    void findAllReturnsEmptyListWhenNoDiets() {
        List<Diet> result = dietService.findAll();
        assertTrue(result.isEmpty());
    }

    @Test
    void saveAndFindById() {
        Diet diet = buildDiet("Test Diet", testProfile);
        Diet saved = dietService.save(diet);

        assertNotNull(saved.getId());
        assertEquals("Test Diet", saved.getName());

        Optional<Diet> found = dietService.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("Test Diet", found.get().getName());
    }

    @Test
    void findAllReturnsSavedDiets() {
        dietService.save(buildDiet("Diet 1", testProfile));
        dietService.save(buildDiet("Diet 2", testProfile));

        List<Diet> result = dietService.findAll();
        assertEquals(2, result.size());
    }

    @Test
    void findByIdReturnsEmptyForNonexistentId() {
        Optional<Diet> result = dietService.findById(UUID.randomUUID());
        assertFalse(result.isPresent());
    }

    @Test
    void deleteByIdRemovesDiet() {
        Diet saved = dietService.save(buildDiet("Test", testProfile));
        dietService.deleteById(saved.getId());

        Optional<Diet> found = dietService.findById(saved.getId());
        assertFalse(found.isPresent());
    }

    @Test
    void addItemPersistsDietItem() {
        Diet saved = dietService.save(buildDiet("Test", testProfile));

        Diet updated = dietService.addItem(
            saved.getId(), testFood.getId(), 200.0, QuantityUnit.GRAMS, "Cocido");

        assertEquals(1, updated.getItems().size());
        DietItem item = updated.getItems().get(0);
        assertEquals(200.0, item.getQuantity());
        assertEquals(QuantityUnit.GRAMS, item.getUnit());
        assertEquals("Cocido", item.getCookingMethod());
    }

    @Test
    void addItemWithInvalidDietIdThrowsException() {
        assertThrows(IllegalArgumentException.class, () ->
            dietService.addItem(UUID.randomUUID(), testFood.getId(), 100.0, QuantityUnit.GRAMS, null));
    }

    @Test
    void removeItemDeletesItemFromDiet() {
        Diet saved = dietService.save(buildDiet("Test", testProfile));
        Diet withItem = dietService.addItem(
            saved.getId(), testFood.getId(), 200.0, QuantityUnit.GRAMS, null);

        UUID itemId = withItem.getItems().get(0).getId();
        Diet afterRemove = dietService.removeItem(withItem.getId(), itemId);

        assertTrue(afterRemove.getItems().isEmpty());
    }

    @Test
    void removeItemWithInvalidItemIdStillWorks() {
        Diet saved = dietService.save(buildDiet("Test", testProfile));
        Diet withItem = dietService.addItem(
            saved.getId(), testFood.getId(), 200.0, QuantityUnit.GRAMS, null);

        Diet afterRemove = dietService.removeItem(withItem.getId(), UUID.randomUUID());
        assertEquals(1, afterRemove.getItems().size());
    }

    @Test
    void calculateTotalNutrientsComputesCorrectly() {
        Food proteinFood = foodService.save(buildValidFood("Protein Source"));
        proteinFood.getNutrients().put(Nutrient.PROTEIN_G, 30.0);
        proteinFood.getNutrients().put(Nutrient.TOTAL_LIPIDS_G, 5.0);
        foodService.save(proteinFood);

        Diet diet = dietService.save(buildDiet("Test", testProfile));
        dietService.addItem(diet.getId(), proteinFood.getId(), 200.0, QuantityUnit.GRAMS, null);

        Diet reloaded = dietService.findById(diet.getId()).orElseThrow();
        Map<Nutrient, Double> totals = dietService.calculateTotalNutrients(reloaded);

        assertEquals(60.0, totals.get(Nutrient.PROTEIN_G), 0.01,
            "30g/100g * 200g = 60g protein");
        assertEquals(10.0, totals.get(Nutrient.TOTAL_LIPIDS_G), 0.01,
            "5g/100g * 200g = 10g lipids");
    }

    @Test
    void calculateTotalNutrientsWithEmptyDiet() {
        Diet diet = dietService.save(buildDiet("Empty", testProfile));

        Diet reloaded = dietService.findById(diet.getId()).orElseThrow();
        Map<Nutrient, Double> totals = dietService.calculateTotalNutrients(reloaded);

        assertTrue(totals.isEmpty());
    }

    @Test
    void calculateTotalNutrientsAggregatesMultipleItems() {
        Food food1 = foodService.save(buildValidFood("Food 1"));
        food1.getNutrients().put(Nutrient.PROTEIN_G, 20.0);
        foodService.save(food1);

        Food food2 = foodService.save(buildValidFood("Food 2"));
        food2.getNutrients().put(Nutrient.PROTEIN_G, 10.0);
        foodService.save(food2);

        Diet diet = dietService.save(buildDiet("Multi", testProfile));
        dietService.addItem(diet.getId(), food1.getId(), 100.0, QuantityUnit.GRAMS, null);
        dietService.addItem(diet.getId(), food2.getId(), 100.0, QuantityUnit.GRAMS, null);

        Diet reloaded = dietService.findById(diet.getId()).orElseThrow();
        Map<Nutrient, Double> totals = dietService.calculateTotalNutrients(reloaded);

        assertEquals(30.0, totals.get(Nutrient.PROTEIN_G), 0.01,
            "20 + 10 = 30g total protein");
    }

    @Test
    void calculateNutrientSummaryReturnsAllNutrients() {
        Diet diet = dietService.save(buildDiet("Test", testProfile));

        Diet reloaded = dietService.findById(diet.getId()).orElseThrow();
        Map<Nutrient, DietService.NutrientSummary> summary =
            dietService.calculateNutrientSummary(reloaded);

        assertNotNull(summary);
        assertEquals(Nutrient.values().length, summary.size());
    }

    @Test
    void calculateNutrientSummaryShowsZeroForEmptyDiet() {
        Diet diet = dietService.save(buildDiet("Empty", testProfile));

        Diet reloaded = dietService.findById(diet.getId()).orElseThrow();
        Map<Nutrient, DietService.NutrientSummary> summary =
            dietService.calculateNutrientSummary(reloaded);

        DietService.NutrientSummary protein = summary.get(Nutrient.PROTEIN_G);
        assertNotNull(protein);
        assertEquals(0.0, protein.total(), 0.01);
        assertNotNull(protein.target());
        assertTrue(protein.target() > 0);
    }

    @Test
    void dietCreatedAtIsSetOnSave() {
        Diet saved = dietService.save(buildDiet("Test", testProfile));
        entityManager.flush();

        assertNotNull(saved.getCreatedAt());
        assertNotNull(saved.getUpdatedAt());
    }

    @Test
    void addItemWithoutCookingMethod() {
        Diet saved = dietService.save(buildDiet("Test", testProfile));
        Diet updated = dietService.addItem(
            saved.getId(), testFood.getId(), 100.0, QuantityUnit.GRAMS, null);

        assertEquals(1, updated.getItems().size());
        assertNull(updated.getItems().get(0).getCookingMethod());
    }

    private Diet buildDiet(String name, AnimalProfile profile) {
        Diet diet = new Diet();
        diet.setName(name);
        diet.setAnimalProfile(profile);
        return diet;
    }

    private AnimalProfile buildValidProfile(String name, Species species) {
        AnimalProfile profile = new AnimalProfile();
        profile.setName(name);
        profile.setSpecies(species);
        profile.setLifeStage(LifeStage.ADULT);
        profile.setWeightKg(20.0);
        profile.setAgeMonths(24);
        profile.setBodyCondition(3);
        profile.setActivityLevel(ActivityLevel.YOUNG_ADULT);
        profile.setAtIdealWeight(true);
        profile.setBlackCoat(false);
        profile.setOverweight(false);
        profile.setUseRecommendedIntake(true);
        profile.setRecommendedCaloricIntake(0.0);
        return profile;
    }

    private Food buildValidFood(String name) {
        Food food = new Food();
        food.setName(name);
        food.setType(FoodType.ANIMAL);
        food.setPortion(100.0);
        food.setPortionUnit(ar.dantezulli.diet_formulator.model.enums.PortionUnit.GRAMS);
        return food;
    }
}
