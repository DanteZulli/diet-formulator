package ar.dantezulli.diet_formulator.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * Macronutrient percentage targets for a diet profile.
 * Porcentajes objetivo de macronutrientes para un perfil de dieta.
 *
 * These values are set via sliders (0-100) in the profile creator.
 * Estos valores se configuran con sliders (0-100) en el creador de perfiles.
 */
@Embeddable
public class MacronutrientTargets {

    /** Protein percentage target. / Porcentaje objetivo de proteína. */
    @Column(name = "target_protein_pct")
    private Double proteinPct;

    /** Fat percentage target. / Porcentaje objetivo de grasa. */
    @Column(name = "target_fat_pct")
    private Double fatPct;

    /** Carbohydrate percentage target. / Porcentaje objetivo de carbohidratos. */
    @Column(name = "target_carbohydrate_pct")
    private Double carbohydratePct;

    /** Fiber percentage of dry matter. / Porcentaje de fibra en materia seca. */
    @Column(name = "target_fiber_ms_pct")
    private Double fiberMsPct;

    public MacronutrientTargets() {}

    public MacronutrientTargets(Double proteinPct, Double fatPct, Double carbohydratePct, Double fiberMsPct) {
        this.proteinPct = proteinPct;
        this.fatPct = fatPct;
        this.carbohydratePct = carbohydratePct;
        this.fiberMsPct = fiberMsPct;
    }

    public Double getProteinPct() { return proteinPct; }
    public void setProteinPct(Double proteinPct) { this.proteinPct = proteinPct; }

    public Double getFatPct() { return fatPct; }
    public void setFatPct(Double fatPct) { this.fatPct = fatPct; }

    public Double getCarbohydratePct() { return carbohydratePct; }
    public void setCarbohydratePct(Double carbohydratePct) { this.carbohydratePct = carbohydratePct; }

    public Double getFiberMsPct() { return fiberMsPct; }
    public void setFiberMsPct(Double fiberMsPct) { this.fiberMsPct = fiberMsPct; }
}
