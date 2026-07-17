package ar.dantezulli.diet_formulator.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PortionUnit {

    GRAMS("Gramos"),
    CAPSULES("Cápsulas"),
    TABLETS("Tabletas"),
    TEASPOONS("Cucharaditas"),
    TABLESPOONS("Cucharadas");

    private final String displayName;
}
