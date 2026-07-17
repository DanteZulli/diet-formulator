package ar.dantezulli.diet_formulator.service;

import org.springframework.stereotype.Service;

import ar.dantezulli.diet_formulator.model.AnimalProfile;
import ar.dantezulli.diet_formulator.model.enums.Especie;
import ar.dantezulli.diet_formulator.model.enums.LifeStage;
import ar.dantezulli.diet_formulator.model.enums.NivelActividad;

/**
 * Calculates daily energy requirements (MER) based on NRC standards.
 *
 * Based on: NRC (2006) Nutrient Requirements of Dogs and Cats.
 *
 * Key formulas:
 * - Adult dog MER: 130 kcal * BW^0.75 (kennel baseline)
 * - Activity factors adjust MER via multipliers
 * - Pregnant bitch (from week 4): MER + 26 kcal * BW^1
 * - Cat MER: ~100 kcal * BW^0.67 (lean cats)
 */
@Service
public class EnergyCalculator {

    /** Base MER coefficient for adult dogs (kennel baseline, NRC 2006). */
    private static final double DOG_MER_COEFFICIENT = 130.0;

    /** Base MER coefficient for cats (lean, NRC 2006). */
    private static final double CAT_MER_COEFFICIENT = 100.0;

    /** Metabolic body weight exponent for dogs. */
    private static final double DOG_EXPONENT = 0.75;

    /** Metabolic body weight exponent for cats. */
    private static final double CAT_EXPONENT = 0.67;

    /** Additional energy for pregnant bitches (kcal per kg BW per day, from week 4). */
    private static final double PREGNANT_ENERGY_PER_KG = 26.0;

    /**
     * Calculates the Maintenance Energy Requirement (MER) in kcal/day.
     *
     * @param profile the animal profile
     * @return MER in kcal/day
     */
    public double calculateMer(AnimalProfile profile) {
        double weightKg = getEffectiveWeight(profile);
        double metabolicWeight = Math.pow(weightKg, getExponent(profile.getEspecie()));

        double mer = getBaseCoefficient(profile.getEspecie()) * metabolicWeight;

        mer = applyLifeStageAdjustment(mer, profile, weightKg);

        return Math.round(mer * 100.0) / 100.0;
    }

    /**
     * Calculates the recommended caloric intake based on activity level.
     *
     * @param profile the animal profile
     * @return recommended kcal/day
     */
    public double calculateRecommendedIntake(AnimalProfile profile) {
        double mer = calculateMer(profile);
        double activityFactor = getActivityFactor(profile.getNivelActividad());
        return Math.round(mer * activityFactor * 100.0) / 100.0;
    }

    /**
     * Gets the effective weight to use in calculations.
     *
     * If the animal is not at its ideal weight, uses the ideal weight.
     *
     * @param profile the animal profile
     * @return effective weight in kg
     */
    private double getEffectiveWeight(AnimalProfile profile) {
        if (Boolean.FALSE.equals(profile.getEnPesoIdeal()) && profile.getPesoIdealKg() != null) {
            return profile.getPesoIdealKg();
        }
        return profile.getPesoKg();
    }

    /**
     * Gets the metabolic body weight exponent based on species.
     */
    private double getExponent(Especie especie) {
        return especie == Especie.GATO ? CAT_EXPONENT : DOG_EXPONENT;
    }

    /**
     * Gets the base MER coefficient based on species.
     */
    private double getBaseCoefficient(Especie especie) {
        return especie == Especie.GATO ? CAT_MER_COEFFICIENT : DOG_MER_COEFFICIENT;
    }

    /**
     * Applies life stage adjustments (pregnancy, lactation, growth).
     *
     * @param mer base MER
     * @param profile the animal profile
     * @param weightKg effective weight in kg
     * @return adjusted MER
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
     *
     * From NRC: additional 26 kcal * BW^1 per day from week 4 after mating.
     *
     * @param mer base MER
     * @param weightKg effective weight in kg
     * @return adjusted MER
     */
    private double applyPregnancyAdjustment(double mer, double weightKg) {
        return mer + (PREGNANT_ENERGY_PER_KG * weightKg);
    }

    /**
     * Applies lactation energy adjustment.
     *
     * Simplified model based on NRC: peak lactation (week 4) with average litter.
     *
     * @param mer base MER
     * @param profile the animal profile
     * @param weightKg effective weight in kg
     * @return adjusted MER
     */
    private double applyLactationAdjustment(double mer, AnimalProfile profile, double weightKg) {
        int numPuppies = profile.getNumCachorrosLactancia() != null ? profile.getNumCachorrosLactancia() : 4;
        int weeksLactation = profile.getSemanasLactancia() != null ? profile.getSemanasLactancia() : 4;

        double milkYieldPctPerPuppy;
        if (numPuppies <= 4) {
            milkYieldPctPerPuppy = 0.01;
        } else if (numPuppies <= 8) {
            milkYieldPctPerPuppy = 0.005;
        } else {
            milkYieldPctPerPuppy = 0.003;
        }

        double weekFactor = switch (weeksLactation) {
            case 1 -> 0.75;
            case 2 -> 0.95;
            case 3 -> 1.10;
            default -> 1.20;
        };

        double milkEnergyKcalPerG = 1.45;
        double dailyMilkYieldG = weightKg * 1000 * milkYieldPctPerPuppy * numPuppies;
        double lactationEnergy = dailyMilkYieldG * milkEnergyKcalPerG * weekFactor;

        return mer + lactationEnergy;
    }

    /**
     * Applies growth energy adjustment for puppies/kittens.
     *
     * Puppies require approximately 2x MER for maintenance.
     *
     * @param mer base MER
     * @param profile the animal profile
     * @return adjusted MER
     */
    private double applyGrowthAdjustment(double mer, AnimalProfile profile) {
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
     *
     * Based on NRC (2006) Table 3-4 and original formulator activity levels.
     *
     * @param nivelActividad activity level
     * @return activity factor (multiplier)
     */
    public double getActivityFactor(NivelActividad nivelActividad) {
        if (nivelActividad == null) return 1.0;

        return switch (nivelActividad) {
            case GRAN_DANES -> 1.6;
            case TERRIER -> 1.4;
            case MUY_ACTIVO -> 1.3;
            case ACTIVO -> 1.2;
            case ADULTO_JOVEN -> 1.0;
            case INACTIVO_SENIOR -> 0.8;
            case CANIL_MUY_INACTIVO -> 0.7;
            case SEDENTARIO -> 0.6;
        };
    }
}
