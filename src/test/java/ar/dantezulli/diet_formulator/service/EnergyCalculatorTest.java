package ar.dantezulli.diet_formulator.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.dantezulli.diet_formulator.model.AnimalProfile;
import ar.dantezulli.diet_formulator.model.enums.Especie;
import ar.dantezulli.diet_formulator.model.enums.LifeStage;
import ar.dantezulli.diet_formulator.model.enums.NivelActividad;

/**
 * Unit tests for EnergyCalculator.
 * Tests unitarios para EnergyCalculator.
 */
class EnergyCalculatorTest {

    private EnergyCalculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new EnergyCalculator();
    }

    // --- Dog tests / Tests de perros ---

    @Test
    void testAdultDogMer() {
        // 21 kg adult dog / Perro adulto de 21 kg
        // Expected: 130 × 21^0.75 ≈ 130 × 9.61 ≈ 1249 kcal
        AnimalProfile profile = createDogProfile(21.0, 252, LifeStage.ADULTO, NivelActividad.ADULTO_JOVEN);
        double mer = calculator.calculateMer(profile);
        assertTrue(mer > 1200 && mer < 1300,
            "MER for 21kg adult dog should be ~1249 kcal, got: " + mer);
    }

    @Test
    void testSmallDogMer() {
        // 5 kg adult dog / Perro adulto de 5 kg
        // Expected: 130 × 5^0.75 ≈ 130 × 3.34 ≈ 434 kcal
        AnimalProfile profile = createDogProfile(5.0, 120, LifeStage.ADULTO, NivelActividad.ADULTO_JOVEN);
        double mer = calculator.calculateMer(profile);
        assertTrue(mer > 400 && mer < 470,
            "MER for 5kg adult dog should be ~434 kcal, got: " + mer);
    }

    @Test
    void testLargeDogMer() {
        // 40 kg adult dog / Perro adulto de 40 kg
        // Expected: 130 × 40^0.75 ≈ 130 × 15.90 ≈ 2067 kcal
        AnimalProfile profile = createDogProfile(40.0, 480, LifeStage.ADULTO, NivelActividad.ADULTO_JOVEN);
        double mer = calculator.calculateMer(profile);
        assertTrue(mer > 2000 && mer < 2150,
            "MER for 40kg adult dog should be ~2067 kcal, got: " + mer);
    }

    // --- Cat tests / Tests de gatos ---

    @Test
    void testAdultCatMer() {
        // 4 kg adult cat / Gato adulto de 4 kg
        // Expected: 100 × 4^0.67 ≈ 100 × 2.52 ≈ 252 kcal
        AnimalProfile profile = createCatProfile(4.0, 120, LifeStage.ADULTO, NivelActividad.ADULTO_JOVEN);
        double mer = calculator.calculateMer(profile);
        assertTrue(mer > 240 && mer < 270,
            "MER for 4kg adult cat should be ~252 kcal, got: " + mer);
    }

    // --- Activity factor tests / Tests de factores de actividad ---

    @Test
    void testActivityFactors() {
        // Activity factors should decrease from highest to lowest / Los factores deben decrecer de mayor a menor
        assertTrue(calculator.getActivityFactor(NivelActividad.GRAN_DANES) >
                   calculator.getActivityFactor(NivelActividad.TERRIER));
        assertTrue(calculator.getActivityFactor(NivelActividad.TERRIER) >
                   calculator.getActivityFactor(NivelActividad.MUY_ACTIVO));
        assertTrue(calculator.getActivityFactor(NivelActividad.MUY_ACTIVO) >
                   calculator.getActivityFactor(NivelActividad.ACTIVO));
        assertTrue(calculator.getActivityFactor(NivelActividad.ACTIVO) >
                   calculator.getActivityFactor(NivelActividad.ADULTO_JOVEN));
        assertTrue(calculator.getActivityFactor(NivelActividad.ADULTO_JOVEN) >
                   calculator.getActivityFactor(NivelActividad.INACTIVO_SENIOR));
        assertTrue(calculator.getActivityFactor(NivelActividad.INACTIVO_SENIOR) >
                   calculator.getActivityFactor(NivelActividad.CANIL_MUY_INACTIVO));
        assertTrue(calculator.getActivityFactor(NivelActividad.CANIL_MUY_INACTIVO) >
                   calculator.getActivityFactor(NivelActividad.SEDENTARIO));
    }

    @Test
    void testRecommendedIntakeWithActivity() {
        // 21 kg dog, sedentary should be less than active
        // Perro de 21 kg, sedentario debe ser menos que activo
        AnimalProfile sedentary = createDogProfile(21.0, 252, LifeStage.ADULTO, NivelActividad.SEDENTARIO);
        AnimalProfile active = createDogProfile(21.0, 252, LifeStage.ADULTO, NivelActividad.ACTIVO);

        double sedentaryIntake = calculator.calculateRecommendedIntake(sedentary);
        double activeIntake = calculator.calculateRecommendedIntake(active);

        assertTrue(sedentaryIntake < activeIntake,
            "Sedentary intake (" + sedentaryIntake + ") should be less than active (" + activeIntake + ")");
    }

    // --- Pregnancy tests / Tests de preñez ---

    @Test
    void testPregnantDogMer() {
        // Pregnant dog should have higher MER than adult
        // Perra preñada debe tener MER mayor que adulto
        AnimalProfile adult = createDogProfile(21.0, 252, LifeStage.ADULTO, NivelActividad.ADULTO_JOVEN);
        AnimalProfile pregnant = createDogProfile(21.0, 252, LifeStage.PREÑADA, NivelActividad.ADULTO_JOVEN);

        double adultMer = calculator.calculateMer(adult);
        double pregnantMer = calculator.calculateMer(pregnant);

        assertTrue(pregnantMer > adultMer,
            "Pregnant MER (" + pregnantMer + ") should be higher than adult MER (" + adultMer + ")");
    }

    // --- Lactation tests / Tests de lactancia ---

    @Test
    void testLactatingDogMer() {
        // Lactating dog with puppies should have much higher MER
        // Perra lactando con cachorros debe tener MER mucho mayor
        AnimalProfile adult = createDogProfile(21.0, 252, LifeStage.ADULTO, NivelActividad.ADULTO_JOVEN);
        AnimalProfile lactating = createDogProfile(21.0, 252, LifeStage.LACTANCIA, NivelActividad.ADULTO_JOVEN);
        lactating.setNumCachorrosLactancia(5);
        lactating.setSemanasLactancia(3);

        double adultMer = calculator.calculateMer(adult);
        double lactatingMer = calculator.calculateMer(lactating);

        assertTrue(lactatingMer > adultMer * 1.5,
            "Lactating MER (" + lactatingMer + ") should be significantly higher than adult (" + adultMer + ")");
    }

    // --- Growth tests / Tests de crecimiento ---

    @Test
    void testPuppyMer() {
        // Puppy should have higher MER per kg than adult
        // Cachorro debe tener MER por kg mayor que adulto
        AnimalProfile puppy = createDogProfile(10.0, 30, LifeStage.CACHORRO, NivelActividad.ACTIVO);
        AnimalProfile adult = createDogProfile(10.0, 120, LifeStage.ADULTO, NivelActividad.ACTIVO);

        double puppyMer = calculator.calculateMer(puppy);
        double adultMer = calculator.calculateMer(adult);

        assertTrue(puppyMer > adultMer,
            "Puppy MER (" + puppyMer + ") should be higher than adult MER (" + adultMer + ")");
    }

    // --- Ideal weight tests / Tests de peso ideal ---

    @Test
    void testUsesIdealWeightWhenNotAtIdeal() {
        // When not at ideal weight, should use ideal weight for calculation
        // Cuando no está en peso ideal, debe usar peso ideal para cálculo
        AnimalProfile overweight = createDogProfile(25.0, 300, LifeStage.ADULTO, NivelActividad.ADULTO_JOVEN);
        overweight.setEnPesoIdeal(false);
        overweight.setPesoIdealKg(21.0);

        AnimalProfile ideal = createDogProfile(21.0, 252, LifeStage.ADULTO, NivelActividad.ADULTO_JOVEN);

        double overweightMer = calculator.calculateMer(overweight);
        double idealMer = calculator.calculateMer(ideal);

        assertEquals(idealMer, overweightMer, 0.1,
            "Should use ideal weight when not at ideal weight");
    }

    // --- Helper methods / Métodos auxiliares ---

    private AnimalProfile createDogProfile(double pesoKg, int edadMeses, LifeStage lifeStage, NivelActividad actividad) {
        AnimalProfile profile = new AnimalProfile();
        profile.setNombre("Test Dog");
        profile.setEspecie(Especie.PERRO);
        profile.setLifeStage(lifeStage);
        profile.setPesoKg(pesoKg);
        profile.setEdadMeses(edadMeses);
        profile.setCondicionCorporal(3);
        profile.setNivelActividad(actividad);
        profile.setEnPesoIdeal(true);
        return profile;
    }

    private AnimalProfile createCatProfile(double pesoKg, int edadMeses, LifeStage lifeStage, NivelActividad actividad) {
        AnimalProfile profile = new AnimalProfile();
        profile.setNombre("Test Cat");
        profile.setEspecie(Especie.GATO);
        profile.setLifeStage(lifeStage);
        profile.setPesoKg(pesoKg);
        profile.setEdadMeses(edadMeses);
        profile.setCondicionCorporal(3);
        profile.setNivelActividad(actividad);
        profile.setEnPesoIdeal(true);
        return profile;
    }
}
