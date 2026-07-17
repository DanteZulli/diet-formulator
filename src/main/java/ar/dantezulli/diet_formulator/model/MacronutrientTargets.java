package ar.dantezulli.diet_formulator.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Macronutrient percentage targets for a diet profile.
 *
 * These values are set via sliders (0-100) in the profile creator.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class MacronutrientTargets {

    @Column(name = "target_protein_pct")
    private Double proteinPct;

    @Column(name = "target_fat_pct")
    private Double fatPct;

    @Column(name = "target_carbohydrate_pct")
    private Double carbohydratePct;

    @Column(name = "target_fiber_ms_pct")
    private Double fiberMsPct;
}
