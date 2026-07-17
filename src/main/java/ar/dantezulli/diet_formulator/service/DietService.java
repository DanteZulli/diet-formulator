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
import ar.dantezulli.diet_formulator.model.enums.Nutrient;
import ar.dantezulli.diet_formulator.model.enums.NutrientLevel;
import ar.dantezulli.diet_formulator.model.enums.QuantityUnit;
import ar.dantezulli.diet_formulator.repository.DietRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class DietService {

    private final DietRepository repository;
    private final NRCRequirementService nrcService;

    @Transactional(readOnly = true)
    public List<Diet> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Diet> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Diet> findByProfileId(Long profileId) {
        return repository.findByAnimalProfileId(profileId);
    }

    public Diet save(Diet diet) {
        return repository.save(diet);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public Diet addItem(Long dietId, Long foodId, Double quantity, QuantityUnit unit, String cookingMethod) {
        Diet diet = repository.findById(dietId)
            .orElseThrow(() -> new IllegalArgumentException("Dieta no encontrada: " + dietId));

        Food food = new Food();
        food.setId(foodId);

        DietItem item = new DietItem(diet, food, quantity, unit);
        item.setCookingMethod(cookingMethod);
        diet.addItem(item);

        return repository.save(diet);
    }

    public Diet removeItem(Long dietId, Long itemId) {
        Diet diet = repository.findById(dietId)
            .orElseThrow(() -> new IllegalArgumentException("Dieta no encontrada: " + dietId));

        diet.getItems().removeIf(item -> item.getId().equals(itemId));
        return repository.save(diet);
    }

    public Map<Nutrient, Double> calculateTotalNutrients(Diet diet) {
        Map<Nutrient, Double> totals = new EnumMap<>(Nutrient.class);

        for (DietItem item : diet.getItems()) {
            Food food = item.getFood();
            Double quantity = item.getQuantity();

            double scale = quantity / 100.0;

            for (Map.Entry<Nutrient, Double> entry : food.getNutrients().entrySet()) {
                totals.merge(entry.getKey(), entry.getValue() * scale, Double::sum);
            }
        }

        return totals;
    }

    public Map<Nutrient, NutrientSummary> calculateNutrientSummary(Diet diet) {
        Map<Nutrient, Double> totals = calculateTotalNutrients(diet);
        AnimalProfile profile = diet.getAnimalProfile();

        Map<Nutrient, NutrientSummary> summary = new EnumMap<>(Nutrient.class);

        for (Nutrient nutrient : Nutrient.values()) {
            Double total = totals.getOrDefault(nutrient, 0.0);
            Double target = nrcService.calculateDailyTarget(profile, nutrient);
            Double sul = nrcService.calculateDailySUL(profile, nutrient);
            Double pctTarget = target != null && target > 0 ? (total / target) * 100 : null;
            NutrientLevel level = nrcService.getNutrientLevel(total, target, sul);

            summary.put(nutrient, new NutrientSummary(total, target, sul, pctTarget, level));
        }

        return summary;
    }

    public record NutrientSummary(
        Double total,
        Double target,
        Double sul,
        Double pctTarget,
        NutrientLevel level
    ) {}
}
