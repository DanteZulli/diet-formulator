package ar.dantezulli.diet_formulator.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.dantezulli.diet_formulator.model.AnimalProfile;
import ar.dantezulli.diet_formulator.model.enums.Species;
import ar.dantezulli.diet_formulator.model.enums.LifeStage;
import ar.dantezulli.diet_formulator.model.enums.ActivityLevel;

class EnergyCalculatorTest {

    private EnergyCalculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new EnergyCalculator();
    }

    @Test
    void testAdultDogMer() {
        AnimalProfile profile = createDogProfile(21.0, 252, LifeStage.ADULT, ActivityLevel.YOUNG_ADULT);
        double mer = calculator.calculateMer(profile);
        assertTrue(mer > 1200 && mer < 1300,
            "MER for 21kg adult dog should be ~1249 kcal, got: " + mer);
    }

    @Test
    void testSmallDogMer() {
        AnimalProfile profile = createDogProfile(5.0, 120, LifeStage.ADULT, ActivityLevel.YOUNG_ADULT);
        double mer = calculator.calculateMer(profile);
        assertTrue(mer > 400 && mer < 470,
            "MER for 5kg adult dog should be ~434 kcal, got: " + mer);
    }

    @Test
    void testLargeDogMer() {
        AnimalProfile profile = createDogProfile(40.0, 480, LifeStage.ADULT, ActivityLevel.YOUNG_ADULT);
        double mer = calculator.calculateMer(profile);
        assertTrue(mer > 2000 && mer < 2150,
            "MER for 40kg adult dog should be ~2067 kcal, got: " + mer);
    }

    @Test
    void testAdultCatMer() {
        AnimalProfile profile = createCatProfile(4.0, 120, LifeStage.ADULT, ActivityLevel.YOUNG_ADULT);
        double mer = calculator.calculateMer(profile);
        assertTrue(mer > 240 && mer < 270,
            "MER for 4kg adult cat should be ~252 kcal, got: " + mer);
    }

    @Test
    void testActivityFactors() {
        assertTrue(calculator.calculateRecommendedIntake(
                       createDogProfile(21.0, 252, LifeStage.ADULT, ActivityLevel.GREAT_DANE)) >
                   calculator.calculateRecommendedIntake(
                       createDogProfile(21.0, 252, LifeStage.ADULT, ActivityLevel.TERRIER)));
        assertTrue(calculator.calculateRecommendedIntake(
                       createDogProfile(21.0, 252, LifeStage.ADULT, ActivityLevel.TERRIER)) >
                   calculator.calculateRecommendedIntake(
                       createDogProfile(21.0, 252, LifeStage.ADULT, ActivityLevel.VERY_ACTIVE)));
        assertTrue(calculator.calculateRecommendedIntake(
                       createDogProfile(21.0, 252, LifeStage.ADULT, ActivityLevel.VERY_ACTIVE)) >
                   calculator.calculateRecommendedIntake(
                       createDogProfile(21.0, 252, LifeStage.ADULT, ActivityLevel.ACTIVE)));
        assertTrue(calculator.calculateRecommendedIntake(
                       createDogProfile(21.0, 252, LifeStage.ADULT, ActivityLevel.ACTIVE)) >
                   calculator.calculateRecommendedIntake(
                       createDogProfile(21.0, 252, LifeStage.ADULT, ActivityLevel.YOUNG_ADULT)));
        assertTrue(calculator.calculateRecommendedIntake(
                       createDogProfile(21.0, 252, LifeStage.ADULT, ActivityLevel.YOUNG_ADULT)) >
                   calculator.calculateRecommendedIntake(
                       createDogProfile(21.0, 252, LifeStage.ADULT, ActivityLevel.INACTIVE_SENIOR)));
        assertTrue(calculator.calculateRecommendedIntake(
                       createDogProfile(21.0, 252, LifeStage.ADULT, ActivityLevel.INACTIVE_SENIOR)) >
                   calculator.calculateRecommendedIntake(
                       createDogProfile(21.0, 252, LifeStage.ADULT, ActivityLevel.KENNEL_INACTIVE)));
        assertTrue(calculator.calculateRecommendedIntake(
                       createDogProfile(21.0, 252, LifeStage.ADULT, ActivityLevel.KENNEL_INACTIVE)) >
                   calculator.calculateRecommendedIntake(
                       createDogProfile(21.0, 252, LifeStage.ADULT, ActivityLevel.SEDENTARY)));
    }

    @Test
    void testRecommendedIntakeWithActivity() {
        AnimalProfile sedentary = createDogProfile(21.0, 252, LifeStage.ADULT, ActivityLevel.SEDENTARY);
        AnimalProfile active = createDogProfile(21.0, 252, LifeStage.ADULT, ActivityLevel.ACTIVE);

        double sedentaryIntake = calculator.calculateRecommendedIntake(sedentary);
        double activeIntake = calculator.calculateRecommendedIntake(active);

        assertTrue(sedentaryIntake < activeIntake,
            "Sedentary intake (" + sedentaryIntake + ") should be less than active (" + activeIntake + ")");
    }

    @Test
    void testPregnantDogMer() {
        AnimalProfile adult = createDogProfile(21.0, 252, LifeStage.ADULT, ActivityLevel.YOUNG_ADULT);
        AnimalProfile pregnant = createDogProfile(21.0, 252, LifeStage.PREGNANT, ActivityLevel.YOUNG_ADULT);

        double adultMer = calculator.calculateMer(adult);
        double pregnantMer = calculator.calculateMer(pregnant);

        assertTrue(pregnantMer > adultMer,
            "Pregnant MER (" + pregnantMer + ") should be higher than adult MER (" + adultMer + ")");
    }

    @Test
    void testLactatingDogMer() {
        AnimalProfile adult = createDogProfile(21.0, 252, LifeStage.ADULT, ActivityLevel.YOUNG_ADULT);
        AnimalProfile lactating = createDogProfile(21.0, 252, LifeStage.LACTATING, ActivityLevel.YOUNG_ADULT);
        lactating.setPuppyCount(5);
        lactating.setLactationWeeks(3);

        double adultMer = calculator.calculateMer(adult);
        double lactatingMer = calculator.calculateMer(lactating);

        assertTrue(lactatingMer > adultMer * 1.5,
            "Lactating MER (" + lactatingMer + ") should be significantly higher than adult (" + adultMer + ")");
    }

    @Test
    void testPuppyMer() {
        AnimalProfile puppy = createDogProfile(10.0, 30, LifeStage.PUPPY, ActivityLevel.ACTIVE);
        AnimalProfile adult = createDogProfile(10.0, 120, LifeStage.ADULT, ActivityLevel.ACTIVE);

        double puppyMer = calculator.calculateMer(puppy);
        double adultMer = calculator.calculateMer(adult);

        assertTrue(puppyMer > adultMer,
            "Puppy MER (" + puppyMer + ") should be higher than adult MER (" + adultMer + ")");
    }

    @Test
    void testUsesIdealWeightWhenNotAtIdeal() {
        AnimalProfile overweight = createDogProfile(25.0, 300, LifeStage.ADULT, ActivityLevel.YOUNG_ADULT);
        overweight.setAtIdealWeight(false);
        overweight.setIdealWeightKg(21.0);

        AnimalProfile ideal = createDogProfile(21.0, 252, LifeStage.ADULT, ActivityLevel.YOUNG_ADULT);

        double overweightMer = calculator.calculateMer(overweight);
        double idealMer = calculator.calculateMer(ideal);

        assertEquals(idealMer, overweightMer, 0.1,
            "Should use ideal weight when not at ideal weight");
    }

    private AnimalProfile createDogProfile(double weightKg, int ageMonths, LifeStage lifeStage, ActivityLevel activityLevel) {
        AnimalProfile profile = new AnimalProfile();
        profile.setName("Test Dog");
        profile.setSpecies(Species.DOG);
        profile.setLifeStage(lifeStage);
        profile.setWeightKg(weightKg);
        profile.setAgeMonths(ageMonths);
        profile.setBodyCondition(3);
        profile.setActivityLevel(activityLevel);
        profile.setAtIdealWeight(true);
        return profile;
    }

    private AnimalProfile createCatProfile(double weightKg, int ageMonths, LifeStage lifeStage, ActivityLevel activityLevel) {
        AnimalProfile profile = new AnimalProfile();
        profile.setName("Test Cat");
        profile.setSpecies(Species.CAT);
        profile.setLifeStage(lifeStage);
        profile.setWeightKg(weightKg);
        profile.setAgeMonths(ageMonths);
        profile.setBodyCondition(3);
        profile.setActivityLevel(activityLevel);
        profile.setAtIdealWeight(true);
        return profile;
    }
}
