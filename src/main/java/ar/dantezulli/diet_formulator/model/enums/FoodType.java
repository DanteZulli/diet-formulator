package ar.dantezulli.diet_formulator.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FoodType {

    ANIMAL("Animal"),
    SUPPLEMENT("Suplemento"),
    COOKED_STARCH("Almidón cocido"),
    OTHER_PLANTS("Otras plantas"),
    RECIPE("Receta");

    private final String displayName;
}
