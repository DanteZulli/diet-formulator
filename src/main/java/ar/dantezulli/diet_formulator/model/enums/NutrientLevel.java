package ar.dantezulli.diet_formulator.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NutrientLevel {

    INSUFFICIENT("belowTarget"),
    ADEQUATE("adequate"),
    EXCESS("excess");

    private final String cssClass;
}
