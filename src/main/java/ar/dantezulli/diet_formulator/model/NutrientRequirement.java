package ar.dantezulli.diet_formulator.model;

import ar.dantezulli.diet_formulator.model.enums.Nutrient;

public record NutrientRequirement(
    Nutrient nutrient,
    Double recommendedAllowancePerKgBW,
    Double safeUpperLimitPerKgBW
) {}
