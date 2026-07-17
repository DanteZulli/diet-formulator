package ar.dantezulli.diet_formulator.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ActivityLevel {

    GREAT_DANE("Gran Danés", 1.6),
    TERRIER("Terrier", 1.4),
    VERY_ACTIVE("Muy activo", 1.3),
    ACTIVE("Activo", 1.2),
    YOUNG_ADULT("Adulto joven", 1.0),
    INACTIVE_SENIOR("Inactivo/senior", 0.8),
    KENNEL_INACTIVE("Canil, muy inactivo", 0.7),
    SEDENTARY("Sedentario", 0.6);

    private final String displayName;
    private final double factor;
}
