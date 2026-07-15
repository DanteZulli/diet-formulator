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

/**
 * Service for Diet CRUD and nutrient calculations.
 * Servicio para CRUD de Dietas y cálculos nutricionales.
 */
@Service
@Transactional
public class DietService {

    private final DietRepository repository;
    private final EnergyCalculator energyCalculator;

    public DietService(DietRepository repository, EnergyCalculator energyCalculator) {
        this.repository = repository;
        this.energyCalculator = energyCalculator;
    }

    /**
     * Returns all diets. / Retorna todas las dietas.
     */
    @Transactional(readOnly = true)
    public List<Diet> findAll() {
        return repository.findAll();
    }

    /**
     * Finds a diet by ID. / Busca una dieta por ID.
     */
    @Transactional(readOnly = true)
    public Optional<Diet> findById(Long id) {
        return repository.findById(id);
    }

    /**
     * Finds all diets for a given profile. / Busca todas las dietas para un perfil dado.
     */
    @Transactional(readOnly = true)
    public List<Diet> findByProfileId(Long profileId) {
        return repository.findByAnimalProfileId(profileId);
    }

    /**
     * Saves a diet. / Guarda una dieta.
     */
    public Diet save(Diet diet) {
        return repository.save(diet);
    }

    /**
     * Deletes a diet by ID. / Elimina una dieta por ID.
     */
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    /**
     * Adds a food item to a diet. / Agrega un ítem de alimento a una dieta.
     *
     * @param dietId the diet ID / ID de la dieta
     * @param foodId the food ID / ID del alimento
     * @param cantidad quantity / cantidad
     * @param unidad unit / unidad
     * @param tipoCoccion cooking type (optional) / tipo de cocción (opcional)
     * @return the updated diet / la dieta actualizada
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
     * Removes an item from a diet. / Elimina un ítem de una dieta.
     */
    public Diet removeItem(Long dietId, Long itemId) {
        Diet diet = repository.findById(dietId)
            .orElseThrow(() -> new IllegalArgumentException("Dieta no encontrada / Diet not found: " + dietId));

        diet.getItems().removeIf(item -> item.getId().equals(itemId));
        return repository.save(diet);
    }

    /**
     * Calculates total nutrient values from all diet items.
     * Calcula valores totales de nutrientes de todos los ítems de la dieta.
     *
     * Each food's nutrients are scaled by (item quantity / 100) since food values are per 100g.
     * Los nutrientes de cada alimento se escalan por (cantidad del ítem / 100) ya que los valores son por 100g.
     *
     * @param diet the diet to calculate / la dieta a calcular
     * @return map of nutrient to total value / mapa de nutriente a valor total
     */
    public Map<Nutrient, Double> calculateTotalNutrients(Diet diet) {
        Map<Nutrient, Double> totals = new EnumMap<>(Nutrient.class);

        for (DietItem item : diet.getItems()) {
            Food food = item.getFood();
            Double quantity = item.getCantidad();

            // Scale factor: quantity in grams / 100 (food values are per 100g)
            // Factor de escala: cantidad en gramos / 100 (valores de alimento son por 100g)
            double scale = quantity / 100.0;

            for (Map.Entry<Nutrient, Double> entry : food.getNutrientes().entrySet()) {
                totals.merge(entry.getKey(), entry.getValue() * scale, Double::sum);
            }
        }

        return totals;
    }

    /**
     * Calculates the nutrient comparison table for a diet.
     * Calcula la tabla de comparación de nutrientes para una dieta.
     *
     * Returns a map of each nutrient to its total, target, and percentage of target.
     * Retorna un mapa de cada nutriente a su total, objetivo, y porcentaje del objetivo.
     *
     * @param diet the diet to analyze / la dieta a analizar
     * @return nutrient summary / resumen de nutrientes
     */
    public Map<Nutrient, NutrientSummary> calculateNutrientSummary(Diet diet) {
        Map<Nutrient, Double> totals = calculateTotalNutrients(diet);
        AnimalProfile profile = diet.getAnimalProfile();
        double recommendedIntake = energyCalculator.calculateRecommendedIntake(profile);

        Map<Nutrient, NutrientSummary> summary = new EnumMap<>(Nutrient.class);

        // Calculate target values from macronutrient targets / Calcular valores objetivo desde objetivos de macros
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
     * Calcula el valor objetivo para un nutriente específico según los objetivos del perfil.
     */
    private Double calculateTarget(Nutrient nutrient, MacronutrientTargets targets, double recommendedIntake) {
        if (targets == null) return null;

        // Only calculate targets for macronutrients based on percentage targets
        // Solo calcular objetivos para macronutrientes basado en porcentajes objetivo
        return switch (nutrient) {
            case PROTEINA_G -> recommendedIntake * (targets.getProteinPct() / 100.0) / 4.0; // 4 kcal/g protein
            case LIPIDOS_TOTALES_G -> recommendedIntake * (targets.getFatPct() / 100.0) / 9.0; // 9 kcal/g fat
            case CARBOHIDRATOS_G -> recommendedIntake * (targets.getCarbohydratePct() / 100.0) / 4.0; // 4 kcal/g carbs
            case ENERGIA_KCAL -> recommendedIntake;
            default -> null; // Other nutrients need NRC tables (not yet implemented)
        };
    }

    /**
     * Summary data for a single nutrient in the diet table.
     * Datos de resumen para un nutriente individual en la tabla de dieta.
     */
    public record NutrientSummary(
        /** Total amount from all food items. / Cantidad total de todos los ítems. */
        Double total,
        /** Target value from profile. / Valor objetivo del perfil. */
        Double target,
        /** Percentage of target achieved (%). / Porcentaje del objetivo alcanzado (%). */
        Double pctTarget
    ) {}
}
