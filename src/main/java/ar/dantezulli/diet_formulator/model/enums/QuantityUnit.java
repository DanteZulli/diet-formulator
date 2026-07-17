package ar.dantezulli.diet_formulator.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum QuantityUnit {

    GRAMS("Gramos"),
    MILLIGRAMS("Miligramos"),
    CAPSULES("Cápsulas"),
    UNITS("Unidades");

    private final String displayName;
}
