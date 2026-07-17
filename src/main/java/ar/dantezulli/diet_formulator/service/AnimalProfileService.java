package ar.dantezulli.diet_formulator.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.dantezulli.diet_formulator.model.AnimalProfile;
import ar.dantezulli.diet_formulator.repository.AnimalProfileRepository;
import lombok.RequiredArgsConstructor;

/**
 * Service for AnimalProfile CRUD operations and energy calculations.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class AnimalProfileService {

    private final AnimalProfileRepository repository;
    private final EnergyCalculator energyCalculator;

    /**
     * Returns all profiles.
     */
    @Transactional(readOnly = true)
    public List<AnimalProfile> findAll() {
        return repository.findAll();
    }

    /**
     * Finds a profile by ID.
     */
    @Transactional(readOnly = true)
    public Optional<AnimalProfile> findById(Long id) {
        return repository.findById(id);
    }

    /**
     * Saves a profile and calculates its caloric intake.
     *
     * @param profile the profile to save
     * @return the saved profile with calculated values
     */
    public AnimalProfile save(AnimalProfile profile) {
        validateProfile(profile);
        calculateCaloricIntake(profile);
        return repository.save(profile);
    }

    /**
     * Deletes a profile by ID.
     */
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    /**
     * Calculates the caloric intake based on profile settings.
     *
     * If "use recommended" is enabled, uses the NRC-calculated value.
     * Otherwise, uses the custom value provided by the veterinarian.
     */
    private void calculateCaloricIntake(AnimalProfile profile) {
        double recommended = energyCalculator.calculateRecommendedIntake(profile);
        profile.setIngestaCaloricaRecomendada(recommended);
    }

    /**
     * Validates profile data before saving.
     *
     * @throws IllegalArgumentException if validation fails
     */
    private void validateProfile(AnimalProfile profile) {
        if (profile.getNombre() == null || profile.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio / Name is required");
        }
        if (profile.getPesoKg() == null || profile.getPesoKg() <= 0) {
            throw new IllegalArgumentException("El peso debe ser mayor a 0 / Weight must be greater than 0");
        }
        if (profile.getEdadMeses() == null || profile.getEdadMeses() < 0) {
            throw new IllegalArgumentException("La edad no puede ser negativa / Age cannot be negative");
        }
        if (profile.getCondicionCorporal() == null ||
            profile.getCondicionCorporal() < 1 || profile.getCondicionCorporal() > 5) {
            throw new IllegalArgumentException("La condición corporal debe ser entre 1 y 5 / Body condition must be between 1 and 5");
        }

        if (profile.getLifeStage() != null) {
            switch (profile.getLifeStage()) {
                case LACTANCIA -> {
                    if (profile.getNumCachorrosLactancia() == null || profile.getNumCachorrosLactancia() <= 0) {
                        throw new IllegalArgumentException(
                            "En lactancia, el número de cachorros es obligatorio / During lactation, number of puppies is required");
                    }
                    if (profile.getSemanasLactancia() == null ||
                        profile.getSemanasLactancia() < 1 || profile.getSemanasLactancia() > 4) {
                        throw new IllegalArgumentException(
                            "Las semanas de lactancia deben ser entre 1 y 4 / Lactation weeks must be between 1 and 4");
                    }
                }
                case CACHORRO -> {
                    if (profile.getPesoAdultoCachorro() == null || profile.getPesoAdultoCachorro() <= 0) {
                        throw new IllegalArgumentException(
                            "Para cachorros, el peso de adulto es obligatorio / For puppies, adult weight is required");
                    }
                }
                default -> { }
            }
        }
    }
}
