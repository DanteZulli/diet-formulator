package ar.dantezulli.diet_formulator.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import ar.dantezulli.diet_formulator.model.entities.AnimalProfile;
import ar.dantezulli.diet_formulator.model.enums.ActivityLevel;
import ar.dantezulli.diet_formulator.model.enums.LifeStage;
import ar.dantezulli.diet_formulator.model.enums.Species;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class AnimalProfileServiceTest {

    @Autowired
    private AnimalProfileService service;

    @Test
    void findAllReturnsEmptyListWhenNoProfiles() {
        List<AnimalProfile> result = service.findAll();
        assertTrue(result.isEmpty());
    }

    @Test
    void saveAndFindById() {
        AnimalProfile saved = service.save(buildValidProfile("Otto", Species.DOG));

        assertNotNull(saved.getId());
        assertEquals("Otto", saved.getName());

        Optional<AnimalProfile> found = service.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("Otto", found.get().getName());
    }

    @Test
    void findAllReturnsSavedProfiles() {
        service.save(buildValidProfile("Otto", Species.DOG));
        service.save(buildValidProfile("Luna", Species.CAT));

        List<AnimalProfile> result = service.findAll();
        assertEquals(2, result.size());
    }

    @Test
    void findByIdReturnsEmptyForNonexistentId() {
        Optional<AnimalProfile> result = service.findById(UUID.randomUUID());
        assertFalse(result.isPresent());
    }

    @Test
    void deleteByIdRemovesProfile() {
        AnimalProfile saved = service.save(buildValidProfile("Otto", Species.DOG));
        service.deleteById(saved.getId());

        Optional<AnimalProfile> found = service.findById(saved.getId());
        assertFalse(found.isPresent());
    }

    @Test
    void saveRejectsBlankName() {
        AnimalProfile profile = buildValidProfile("", Species.DOG);
        assertThrows(IllegalArgumentException.class, () -> service.save(profile));
    }

    @Test
    void saveRejectsNullName() {
        AnimalProfile profile = buildValidProfile(null, Species.DOG);
        assertThrows(IllegalArgumentException.class, () -> service.save(profile));
    }

    @Test
    void saveRejectsZeroWeight() {
        AnimalProfile profile = buildValidProfile("Test", Species.DOG);
        profile.setWeightKg(0.0);
        assertThrows(IllegalArgumentException.class, () -> service.save(profile));
    }

    @Test
    void saveRejectsNegativeWeight() {
        AnimalProfile profile = buildValidProfile("Test", Species.DOG);
        profile.setWeightKg(-5.0);
        assertThrows(IllegalArgumentException.class, () -> service.save(profile));
    }

    @Test
    void saveRejectsNegativeAge() {
        AnimalProfile profile = buildValidProfile("Test", Species.DOG);
        profile.setAgeMonths(-1);
        assertThrows(IllegalArgumentException.class, () -> service.save(profile));
    }

    @Test
    void saveRejectsBodyConditionBelowRange() {
        AnimalProfile profile = buildValidProfile("Test", Species.DOG);
        profile.setBodyCondition(0);
        assertThrows(IllegalArgumentException.class, () -> service.save(profile));
    }

    @Test
    void saveRejectsBodyConditionAboveRange() {
        AnimalProfile profile = buildValidProfile("Test", Species.DOG);
        profile.setBodyCondition(6);
        assertThrows(IllegalArgumentException.class, () -> service.save(profile));
    }

    @Test
    void saveAcceptsBodyConditionAtBoundaries() {
        AnimalProfile low = buildValidProfile("Low", Species.DOG);
        low.setBodyCondition(1);
        assertNotNull(service.save(low));

        AnimalProfile high = buildValidProfile("High", Species.DOG);
        high.setBodyCondition(5);
        assertNotNull(service.save(high));
    }

    @Test
    void saveRejectsLactatingWithoutPuppyCount() {
        AnimalProfile profile = buildValidProfile("Mama", Species.DOG);
        profile.setLifeStage(LifeStage.LACTATING);
        profile.setPuppyCount(null);
        profile.setLactationWeeks(3);
        assertThrows(IllegalArgumentException.class, () -> service.save(profile));
    }

    @Test
    void saveRejectsLactatingWithZeroPuppies() {
        AnimalProfile profile = buildValidProfile("Mama", Species.DOG);
        profile.setLifeStage(LifeStage.LACTATING);
        profile.setPuppyCount(0);
        profile.setLactationWeeks(3);
        assertThrows(IllegalArgumentException.class, () -> service.save(profile));
    }

    @Test
    void saveRejectsLactatingWithInvalidWeeks() {
        AnimalProfile profile = buildValidProfile("Mama", Species.DOG);
        profile.setLifeStage(LifeStage.LACTATING);
        profile.setPuppyCount(4);
        profile.setLactationWeeks(0);
        assertThrows(IllegalArgumentException.class, () -> service.save(profile));

        profile.setLactationWeeks(5);
        assertThrows(IllegalArgumentException.class, () -> service.save(profile));
    }

    @Test
    void saveAcceptsValidLactatingProfile() {
        AnimalProfile profile = buildValidProfile("Mama", Species.DOG);
        profile.setLifeStage(LifeStage.LACTATING);
        profile.setPuppyCount(4);
        profile.setLactationWeeks(3);

        AnimalProfile saved = service.save(profile);
        assertNotNull(saved.getId());
        assertNotNull(saved.getRecommendedCaloricIntake());
        assertTrue(saved.getRecommendedCaloricIntake() > 0);
    }

    @Test
    void saveRejectsPuppyWithoutAdultWeight() {
        AnimalProfile profile = buildValidProfile("Puppy", Species.DOG);
        profile.setLifeStage(LifeStage.PUPPY);
        profile.setAdultWeightPuppy(null);
        assertThrows(IllegalArgumentException.class, () -> service.save(profile));
    }

    @Test
    void saveRejectsPuppyWithZeroAdultWeight() {
        AnimalProfile profile = buildValidProfile("Puppy", Species.DOG);
        profile.setLifeStage(LifeStage.PUPPY);
        profile.setAdultWeightPuppy(0.0);
        assertThrows(IllegalArgumentException.class, () -> service.save(profile));
    }

    @Test
    void saveAcceptsValidPuppyProfile() {
        AnimalProfile profile = buildValidProfile("Puppy", Species.DOG);
        profile.setLifeStage(LifeStage.PUPPY);
        profile.setAgeMonths(3);
        profile.setAdultWeightPuppy(25.0);

        AnimalProfile saved = service.save(profile);
        assertNotNull(saved.getId());
        assertNotNull(saved.getRecommendedCaloricIntake());
        assertTrue(saved.getRecommendedCaloricIntake() > 0);
    }

    @Test
    void saveCalculatesRecommendedCaloricIntake() {
        AnimalProfile profile = buildValidProfile("Otto", Species.DOG);

        AnimalProfile saved = service.save(profile);

        assertNotNull(saved.getRecommendedCaloricIntake());
        assertTrue(saved.getRecommendedCaloricIntake() > 0,
            "Caloric intake should be positive for valid profile");
    }

    private AnimalProfile buildValidProfile(String name, Species species) {
        AnimalProfile profile = new AnimalProfile();
        profile.setName(name);
        profile.setSpecies(species);
        profile.setLifeStage(LifeStage.ADULT);
        profile.setWeightKg(20.0);
        profile.setAgeMonths(24);
        profile.setBodyCondition(3);
        profile.setActivityLevel(ActivityLevel.YOUNG_ADULT);
        profile.setAtIdealWeight(true);
        profile.setBlackCoat(false);
        profile.setOverweight(false);
        profile.setUseRecommendedIntake(true);
        profile.setRecommendedCaloricIntake(0.0);
        return profile;
    }
}
