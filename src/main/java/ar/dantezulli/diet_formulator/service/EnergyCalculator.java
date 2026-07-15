package ar.dantezulli.diet_formulator.service;

import org.springframework.stereotype.Service;

import ar.dantezulli.diet_formulator.model.AnimalProfile;
import ar.dantezulli.diet_formulator.model.enums.Especie;
import ar.dantezulli.diet_formulator.model.enums.LifeStage;
import ar.dantezulli.diet_formulator.model.enums.NivelActividad;

/**
 * Calculates daily energy requirements (MER) based on NRC standards.
 * Calcula los requisitos energéticos diarios (MER) según estándares NRC.
 *
 * Based on: NRC (2006) Nutrient Requirements of Dogs and Cats.
 * Basado en: NRC (2006) Nutrient Requirements of Dogs and Cats.
 *
 * Key formulas / Fórmulas clave:
 * - Adult dog MER: 130 kcal × BW^0.75 (kennel baseline) / MER perro adulto: 130 kcal × BW^0.75
 * - Activity factors adjust MER via multipliers / Factores de actividad ajustan MER con multiplicadores
 * - Pregnant bitch (from week 4): MER + 26 kcal × BW^1 / Perra preñada (desde semana 4): MER + 26 kcal × BW^1
 * - Cat MER: ~100 kcal × BW^0.67 (lean cats) / MER gato: ~100 kcal × BW^0.67 (gatos delgados)
 */
@Service
public class EnergyCalculator {

    /** Base MER coefficient for adult dogs (kennel baseline, NRC 2006). / Coeficiente MER base para perros adultos. */
    private static final double DOG_MER_COEFFICIENT = 130.0;

    /** Base MER coefficient for cats (lean, NRC 2006). / Coeficiente MER base para gatos (delgados). */
    private static final double CAT_MER_COEFFICIENT = 100.0;

    /** Metabolic body weight exponent for dogs. / Exponente de peso metabólico para perros. */
    private static final double DOG_EXPONENT = 0.75;

    /** Metabolic body weight exponent for cats. / Exponente de peso metabólico para gatos. */
    private static final double CAT_EXPONENT = 0.67;

    /** Additional energy for pregnant bitches (kcal per kg BW per day, from week 4). / Energía adicional para perras preñadas (kcal por kg de peso por día, desde semana 4). */
    private static final double PREGNANT_ENERGY_PER_KG = 26.0;

    /**
     * Calculates the Maintenance Energy Requirement (MER) in kcal/day.
     * Calcula el Requisito Energético de Mantenimiento (MER) en kcal/día.
     *
     * @param profile the animal profile / el perfil del animal
     * @return MER in kcal/day / MER en kcal/día
     */
    public double calculateMer(AnimalProfile profile) {
        double weightKg = getEffectiveWeight(profile);
        double metabolicWeight = Math.pow(weightKg, getExponent(profile.getEspecie()));

        double mer = getBaseCoefficient(profile.getEspecie()) * metabolicWeight;

        // Apply life stage adjustments / Aplicar ajustes por etapa de vida
        mer = applyLifeStageAdjustment(mer, profile, weightKg);

        return Math.round(mer * 100.0) / 100.0; // Round to 2 decimals / Redondear a 2 decimales
    }

    /**
     * Calculates the recommended caloric intake based on activity level.
     * Calcula la ingesta calórica recomendada según el nivel de actividad.
     *
     * @param profile the animal profile / el perfil del animal
     * @return recommended kcal/day / kcal/día recomendados
     */
    public double calculateRecommendedIntake(AnimalProfile profile) {
        double mer = calculateMer(profile);
        double activityFactor = getActivityFactor(profile.getNivelActividad());
        return Math.round(mer * activityFactor * 100.0) / 100.0;
    }

    /**
     * Gets the effective weight to use in calculations.
     * Obtiene el peso efectivo a usar en los cálculos.
     *
     * If the animal is not at its ideal weight, uses the ideal weight.
     * Si el animal no está en su peso ideal, usa el peso ideal.
     *
     * @param profile the animal profile / el perfil del animal
     * @return effective weight in kg / peso efectivo en kg
     */
    private double getEffectiveWeight(AnimalProfile profile) {
        if (Boolean.FALSE.equals(profile.getEnPesoIdeal()) && profile.getPesoIdealKg() != null) {
            return profile.getPesoIdealKg();
        }
        return profile.getPesoKg();
    }

    /**
     * Gets the metabolic body weight exponent based on species.
     * Obtiene el exponente de peso metabólico según la especie.
     */
    private double getExponent(Especie especie) {
        return especie == Especie.GATO ? CAT_EXPONENT : DOG_EXPONENT;
    }

    /**
     * Gets the base MER coefficient based on species.
     * Obtiene el coeficiente MER base según la especie.
     */
    private double getBaseCoefficient(Especie especie) {
        return especie == Especie.GATO ? CAT_MER_COEFFICIENT : DOG_MER_COEFFICIENT;
    }

    /**
     * Applies life stage adjustments (pregnancy, lactation, growth).
     * Aplica ajustes por etapa de vida (preñez, lactancia, crecimiento).
     *
     * @param mer base MER / MER base
     * @param profile the animal profile / el perfil del animal
     * @param weightKg effective weight in kg / peso efectivo en kg
     * @return adjusted MER / MER ajustado
     */
    private double applyLifeStageAdjustment(double mer, AnimalProfile profile, double weightKg) {
        LifeStage stage = profile.getLifeStage();

        return switch (stage) {
            case PREÑADA -> applyPregnancyAdjustment(mer, weightKg);
            case LACTANCIA -> applyLactationAdjustment(mer, profile, weightKg);
            case CACHORRO -> applyGrowthAdjustment(mer, profile);
            case ADULTO -> mer;
        };
    }

    /**
     * Applies pregnancy energy adjustment.
     * Aplica ajuste de energía para preñez.
     *
     * From NRC: additional 26 kcal × BW^1 per day from week 4 after mating.
     * Desde NRC: 26 kcal × BW^1 adicionales por día desde la semana 4 después de la monta.
     *
     * @param mer base MER / MER base
     * @param weightKg effective weight in kg / peso efectivo en kg
     * @return adjusted MER / MER ajustado
     */
    private double applyPregnancyAdjustment(double mer, double weightKg) {
        return mer + (PREGNANT_ENERGY_PER_KG * weightKg);
    }

    /**
     * Applies lactation energy adjustment.
     * Aplica ajuste de energía para lactancia.
     *
     * Simplified model based on NRC: peak lactation (week 4) with average litter.
     * Modelo simplificado basado en NRC: lactancia pico (semana 4) con camada promedio.
     *
     * @param mer base MER / MER base
     * @param profile the animal profile / el perfil del animal
     * @param weightKg effective weight in kg / peso efectivo en kg
     * @return adjusted MER / MER ajustado
     */
    private double applyLactationAdjustment(double mer, AnimalProfile profile, double weightKg) {
        int numPuppies = profile.getNumCachorrosLactancia() != null ? profile.getNumCachorrosLactancia() : 4;
        int weeksLactation = profile.getSemanasLactancia() != null ? profile.getSemanasLactancia() : 4;

        // Milk yield as % of BW per puppy / Producción de leche como % del peso por cachorro
        double milkYieldPctPerPuppy;
        if (numPuppies <= 4) {
            milkYieldPctPerPuppy = 0.01; // ~1% BW per puppy / ~1% peso por cachorro
        } else if (numPuppies <= 8) {
            milkYieldPctPerPuppy = 0.005; // ~0.5% BW per puppy / ~0.5% peso por cachorro
        } else {
            milkYieldPctPerPuppy = 0.003; // diminishing returns / rendimientos decrecientes
        }

        // Week factor (peak at week 4) / Factor semanal (pico en semana 4)
        double weekFactor = switch (weeksLactation) {
            case 1 -> 0.75;
            case 2 -> 0.95;
            case 3 -> 1.10;
            default -> 1.20; // week 4+ / semana 4+
        };

        double milkEnergyKcalPerG = 1.45; // Energy content of dog milk / Contenido energético de leche canina
        double dailyMilkYieldG = weightKg * 1000 * milkYieldPctPerPuppy * numPuppies;
        double lactationEnergy = dailyMilkYieldG * milkEnergyKcalPerG * weekFactor;

        return mer + lactationEnergy;
    }

    /**
     * Applies growth energy adjustment for puppies/kittens.
     * Aplica ajuste de energía para crecimiento de cachorros/gatitos.
     *
     * Puppies require approximately 2x MER for maintenance.
     * Los cachorros requieren aproximadamente 2x MER para mantenimiento.
     *
     * @param mer base MER / MER base
     * @param profile the animal profile / el perfil del animal
     * @return adjusted MER / MER ajustado
     */
    private double applyGrowthAdjustment(double mer, AnimalProfile profile) {
        // Younger puppies need more energy / Cachorros más jóvenes necesitan más energía
        if (profile.getEdadMeses() <= 4) {
            return mer * 2.5;
        } else if (profile.getEdadMeses() <= 8) {
            return mer * 2.0;
        } else {
            return mer * 1.6;
        }
    }

    /**
     * Returns the activity factor multiplier for the given activity level.
     * Retorna el multiplicador de factor de actividad para el nivel dado.
     *
     * Based on NRC (2006) Table 3-4 and original formulator activity levels.
     * Basado en NRC (2006) Tabla 3-4 y niveles de actividad del formulador original.
     *
     * @param nivelActividad activity level / nivel de actividad
     * @return activity factor (multiplier) / factor de actividad (multiplicador)
     */
    public double getActivityFactor(NivelActividad nivelActividad) {
        if (nivelActividad == null) return 1.0;

        return switch (nivelActividad) {
            case GRAN_DANES -> 1.6;    // Great Danes: 60% above average / Gran Danés: 60% sobre promedio
            case TERRIER -> 1.4;       // Terriers: above average / Terriers: sobre promedio
            case MUY_ACTIVO -> 1.3;    // Very active / Muy activo
            case ACTIVO -> 1.2;        // Active / Activo
            case ADULTO_JOVEN -> 1.0;  // Young adult (baseline) / Adulto joven (línea base)
            case INACTIVO_SENIOR -> 0.8; // Inactive/senior: 20% below / Inactivo/senior: 20% menos
            case CANIL_MUY_INACTIVO -> 0.7; // Kennel, very inactive / Canil, muy inactivo
            case SEDENTARIO -> 0.6;    // Sedentary / Sedentario
        };
    }
}
