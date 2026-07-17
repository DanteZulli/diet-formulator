package ar.dantezulli.diet_formulator.service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.dantezulli.diet_formulator.model.AnimalProfile;
import ar.dantezulli.diet_formulator.model.Diet;
import ar.dantezulli.diet_formulator.model.DietItem;
import ar.dantezulli.diet_formulator.model.Food;
import ar.dantezulli.diet_formulator.model.MacronutrientTargets;
import ar.dantezulli.diet_formulator.model.enums.Nutrient;
import ar.dantezulli.diet_formulator.model.enums.UnidadCantidad;
import ar.dantezulli.diet_formulator.repository.DietRepository;
import lombok.RequiredArgsConstructor;

/**
 * Service for Diet CRUD and nutrient calculations.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class DietService {

    private final DietRepository repository;
    private final EnergyCalculator energyCalculator;

    /**
     * Returns all diets.
     */
    @Transactional(readOnly = true)
    public List<Diet> findAll() {
        return repository.findAll();
    }

    /**
     * Finds a diet by ID.
     */
    @Transactional(readOnly = true)
    public Optional<Diet> findById(Long id) {
        return repository.findById(id);
    }

    /**
     * Finds all diets for a given profile.
     */
    @Transactional(readOnly = true)
    public List<Diet> findByProfileId(Long profileId) {
        return repository.findByAnimalProfileId(profileId);
    }

    /**
     * Saves a diet.
     */
    public Diet save(Diet diet) {
        return repository.save(diet);
    }

    /**
     * Deletes a diet by ID.
     */
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    /**
     * Adds a food item to a diet.
     *
     * @param dietId the diet ID
     * @param foodId the food ID
     * @param cantidad quantity
     * @param unidad unit
     * @param tipoCoccion cooking type (optional)
     * @return the updated diet
     */
    public Diet addItem(Long dietId, Long foodId, Double cantidad, UnidadCantidad unidad, String tipoCoccion) {
        Diet diet = repository.findById(dietId)
            .orElseThrow(() -> new IllegalArgumentException("Dieta no encontrada / Diet not found: " + dietId));

        Food food = new Food();
        food.setId(foodId);

        DietItem item = new DietItem(diet, food, cantidad, unidad);
        item.setTipoCoccion(tipoCoccion);
        diet.addItem(item);

        return repository.save(diet);
    }

    /**
     * Removes an item from a diet.
     */
    public Diet removeItem(Long dietId, Long itemId) {
        Diet diet = repository.findById(dietId)
            .orElseThrow(() -> new IllegalArgumentException("Dieta no encontrada / Diet not found: " + dietId));

        diet.getItems().removeIf(item -> item.getId().equals(itemId));
        return repository.save(diet);
    }

    /**
     * Calculates total nutrient values from all diet items.
     *
     * Each food's nutrients are scaled by (item quantity / 100) since food values are per 100g.
     *
     * @param diet the diet to calculate
     * @return map of nutrient to total value
     */
    public Map<Nutrient, Double> calculateTotalNutrients(Diet diet) {
        Map<Nutrient, Double> totals = new EnumMap<>(Nutrient.class);

        for (DietItem item : diet.getItems()) {
            Food food = item.getFood();
            Double quantity = item.getCantidad();

            double scale = quantity / 100.0;

            for (Map.Entry<Nutrient, Double> entry : food.getNutrientes().entrySet()) {
                totals.merge(entry.getKey(), entry.getValue() * scale, Double::sum);
            }
        }

        return totals;
    }

    /**
     * Calculates the nutrient comparison table for a diet.
     *
     * Returns a map of each nutrient to its total, target, and percentage of target.
     *
     * @param diet the diet to analyze
     * @return nutrient summary
     */
    public Map<Nutrient, NutrientSummary> calculateNutrientSummary(Diet diet) {
        Map<Nutrient, Double> totals = calculateTotalNutrients(diet);
        AnimalProfile profile = diet.getAnimalProfile();
        double recommendedIntake = energyCalculator.calculateRecommendedIntake(profile);

        Map<Nutrient, NutrientSummary> summary = new EnumMap<>(Nutrient.class);

        MacronutrientTargets targets = profile.getMacronutrientTargets();

        for (Nutrient nutrient : Nutrient.values()) {
            Double total = totals.getOrDefault(nutrient, 0.0);
            Double target = calculateTarget(nutrient, targets, recommendedIntake);
            Double pctTarget = target != null && target > 0 ? (total / target) * 100 : null;

            summary.put(nutrient, new NutrientSummary(total, target, pctTarget));
        }

        return summary;
    }

    /**
     * Calculates the target value for a specific nutrient based on profile targets.
     */
    private Double calculateTarget(Nutrient nutrient, MacronutrientTargets targets, double recommendedIntake) {
        if (targets == null) return null;

        return switch (nutrient) {
            case PROTEINA_G -> recommendedIntake * (targets.getProteinPct() / 100.0) / 4.0;
            case LIPIDOS_TOTALES_G -> recommendedIntake * (targets.getFatPct() / 100.0) / 9.0;
            case CARBOHIDRATOS_G -> recommendedIntake * (targets.getCarbohydratePct() / 100.0) / 4.0;
            case ENERGIA_KCAL -> recommendedIntake;
            default -> null;
        };
    }

    /**
     * Summary data for a single nutrient in the diet table.
     */
    public record NutrientSummary(
        /** Total amount from all food items. */
        Double total,
        /** Target value from profile. */
        Double target,
        /** Percentage of target achieved (%). */
        Double pctTarget
    ) {}
}
