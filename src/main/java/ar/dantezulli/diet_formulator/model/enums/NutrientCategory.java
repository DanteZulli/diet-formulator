package ar.dantezulli.diet_formulator.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NutrientCategory {

    ENERGY("Energía"),
    PROTEIN_AMINO_ACIDS("Proteínas y Aminoácidos"),
    LIPIDS_FATTY_ACIDS("Lípidos y Ácidos Grasos"),
    CARBOHYDRATES("Carbohidratos"),
    MINERALS("Minerales"),
    VITAMINS("Vitaminas");

    private final String displayName;
}
