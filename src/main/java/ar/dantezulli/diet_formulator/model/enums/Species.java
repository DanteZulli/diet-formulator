package ar.dantezulli.diet_formulator.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Species {

    DOG("Perro"),
    CAT("Gato");

    private final String displayName;
}
