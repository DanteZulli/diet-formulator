package ar.dantezulli.diet_formulator.service;

import org.springframework.stereotype.Service;

import ar.dantezulli.diet_formulator.model.AnimalProfile;
import ar.dantezulli.diet_formulator.model.enums.Species;
import ar.dantezulli.diet_formulator.model.enums.LifeStage;
import ar.dantezulli.diet_formulator.model.enums.ActivityLevel;

@Service
public class EnergyCalculator {

    private static final double DOG_MER_COEFFICIENT = 130.0;
    private static final double CAT_MER_COEFFICIENT = 100.0;
    private static final double DOG_EXPONENT = 0.75;
    private static final double CAT_EXPONENT = 0.67;
    private static final double PREGNANT_ENERGY_PER_KG = 26.0;

    public double calculateMer(AnimalProfile profile) {
        double weightKg = getEffectiveWeight(profile);
        double metabolicWeight = Math.pow(weightKg, getExponent(profile.getSpecies()));

        double mer = getBaseCoefficient(profile.getSpecies()) * metabolicWeight;

        mer = applyLifeStageAdjustment(mer, profile, weightKg);

        return Math.round(mer * 100.0) / 100.0;
    }

    public double calculateRecommendedIntake(AnimalProfile profile) {
        double mer = calculateMer(profile);
        double activityFactor = profile.getActivityLevel().getFactor();
        return Math.round(mer * activityFactor * 100.0) / 100.0;
    }

    private double getEffectiveWeight(AnimalProfile profile) {
        if (Boolean.FALSE.equals(profile.getAtIdealWeight()) && profile.getIdealWeightKg() != null) {
            return profile.getIdealWeightKg();
        }
        return profile.getWeightKg();
    }

    private double getExponent(Species species) {
        return species == Species.CAT ? CAT_EXPONENT : DOG_EXPONENT;
    }

    private double getBaseCoefficient(Species species) {
        return species == Species.CAT ? CAT_MER_COEFFICIENT : DOG_MER_COEFFICIENT;
    }

    private double applyLifeStageAdjustment(double mer, AnimalProfile profile, double weightKg) {
        LifeStage stage = profile.getLifeStage();

        return switch (stage) {
            case PREGNANT -> applyPregnancyAdjustment(mer, weightKg);
            case LACTATING -> applyLactationAdjustment(mer, profile, weightKg);
            case PUPPY -> applyGrowthAdjustment(mer, profile);
            case ADULT -> mer;
        };
    }

    private double applyPregnancyAdjustment(double mer, double weightKg) {
        return mer + (PREGNANT_ENERGY_PER_KG * weightKg);
    }

    private double applyLactationAdjustment(double mer, AnimalProfile profile, double weightKg) {
        int numPuppies = profile.getPuppyCount() != null ? profile.getPuppyCount() : 4;
        int weeksLactation = profile.getLactationWeeks() != null ? profile.getLactationWeeks() : 4;

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

    private double applyGrowthAdjustment(double mer, AnimalProfile profile) {
        if (profile.getAgeMonths() <= 4) {
            return mer * 2.5;
        } else if (profile.getAgeMonths() <= 8) {
            return mer * 2.0;
        } else {
            return mer * 1.6;
        }
    }
}
