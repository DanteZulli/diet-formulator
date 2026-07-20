package ar.dantezulli.diet_formulator.model.records;

import ar.dantezulli.diet_formulator.model.enums.Nutrient;

public record NutrientRequirement(
    Nutrient nutrient,
    Double recommendedAllowancePerKgBW,
    Double safeUpperLimitPerKgBW
) {}
