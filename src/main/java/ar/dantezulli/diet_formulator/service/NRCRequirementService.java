package ar.dantezulli.diet_formulator.service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import ar.dantezulli.diet_formulator.data.NRCRequirementData;
import ar.dantezulli.diet_formulator.model.entities.AnimalProfile;
import ar.dantezulli.diet_formulator.model.records.NutrientRequirement;
import ar.dantezulli.diet_formulator.model.enums.LifeStage;
import ar.dantezulli.diet_formulator.model.enums.Nutrient;
import ar.dantezulli.diet_formulator.model.enums.NutrientLevel;
import ar.dantezulli.diet_formulator.model.enums.Species;

/**
 * Calculates daily nutrient targets from NRC (2006) Recommended Allowances.
 *
 * Formula: target = RA_per_kg_BW_exp * (effectiveBW ^ exponent)
 *   Dogs: exponent = 0.75
 *   Cats: exponent = 0.67
 *
 * Effective body weight = idealWeightKg if atIdealWeight == false, else weightKg.
 */
@Service
public class NRCRequirementService {

    private static final double DOG_EXPONENT = 0.75;
    private static final double CAT_EXPONENT = 0.67;

    private final Map<Species, Map<LifeStage, List<NutrientRequirement>>> cache = new EnumMap<>(Species.class);

    public NRCRequirementService() {
        for (Species species : Species.values()) {
            Map<LifeStage, List<NutrientRequirement>> byStage = new EnumMap<>(LifeStage.class);
            for (LifeStage stage : LifeStage.values()) {
                byStage.put(stage, NRCRequirementData.getRequirements(species, stage));
            }
            cache.put(species, byStage);
        }
    }

    /**
     * RA per kg BW^exp for a given nutrient, or null if no NRC requirement exists.
     */
    private Double getRecommendedAllowancePerKgBW(Species species, LifeStage lifeStage, Nutrient nutrient) {
        List<NutrientRequirement> reqs = cache.getOrDefault(species, Map.of())
                .getOrDefault(lifeStage, List.of());
        for (NutrientRequirement req : reqs) {
            if (req.nutrient() == nutrient) {
                return req.recommendedAllowancePerKgBW();
            }
        }
        return null;
    }

    /**
     * SUL per kg BW^exp for a given nutrient, or null if no safe upper limit is defined.
     */
    private Double getSafeUpperLimitPerKgBW(Species species, LifeStage lifeStage, Nutrient nutrient) {
        List<NutrientRequirement> reqs = cache.getOrDefault(species, Map.of())
                .getOrDefault(lifeStage, List.of());
        for (NutrientRequirement req : reqs) {
            if (req.nutrient() == nutrient) {
                return req.safeUpperLimitPerKgBW();
            }
        }
        return null;
    }

    /**
     * Absolute daily target in nutrient's native unit (g, mg, or ug).
     * Returns null if no NRC requirement exists for this nutrient.
     */
    public Double calculateDailyTarget(AnimalProfile profile, Nutrient nutrient) {
        Double raPerKg = getRecommendedAllowancePerKgBW(
                profile.getSpecies(), profile.getLifeStage(), nutrient);
        if (raPerKg == null) return null;

        double effectiveBW = getEffectiveBodyWeight(profile);
        double exponent = profile.getSpecies() == Species.DOG ? DOG_EXPONENT : CAT_EXPONENT;
        return raPerKg * Math.pow(effectiveBW, exponent);
    }

    /**
     * Absolute daily SUL in nutrient's native unit, or null if undefined.
     */
    public Double calculateDailySUL(AnimalProfile profile, Nutrient nutrient) {
        Double sulPerKg = getSafeUpperLimitPerKgBW(
                profile.getSpecies(), profile.getLifeStage(), nutrient);
        if (sulPerKg == null) return null;

        double effectiveBW = getEffectiveBodyWeight(profile);
        double exponent = profile.getSpecies() == Species.DOG ? DOG_EXPONENT : CAT_EXPONENT;
        return sulPerKg * Math.pow(effectiveBW, exponent);
    }

    public NutrientLevel getNutrientLevel(Double total, Double target, Double sul) {
        if (target == null || target <= 0) return null;
        if (total == null) return NutrientLevel.INSUFFICIENT;
        if (total < target) return NutrientLevel.INSUFFICIENT;
        if (sul != null && sul > 0 && total > sul) return NutrientLevel.EXCESS;
        return NutrientLevel.ADEQUATE;
    }

    private double getEffectiveBodyWeight(AnimalProfile profile) {
        if (!profile.getAtIdealWeight() && profile.getIdealWeightKg() != null) {
            return profile.getIdealWeightKg();
        }
        return profile.getWeightKg();
    }
}
