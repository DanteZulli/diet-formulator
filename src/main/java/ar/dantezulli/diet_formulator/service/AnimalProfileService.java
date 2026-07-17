package ar.dantezulli.diet_formulator.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.dantezulli.diet_formulator.model.AnimalProfile;
import ar.dantezulli.diet_formulator.repository.AnimalProfileRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AnimalProfileService {

    private final AnimalProfileRepository repository;
    private final EnergyCalculator energyCalculator;

    @Transactional(readOnly = true)
    public List<AnimalProfile> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<AnimalProfile> findById(Long id) {
        return repository.findById(id);
    }

    public AnimalProfile save(AnimalProfile profile) {
        validateProfile(profile);
        calculateCaloricIntake(profile);
        return repository.save(profile);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    private void calculateCaloricIntake(AnimalProfile profile) {
        double recommended = energyCalculator.calculateRecommendedIntake(profile);
        profile.setRecommendedCaloricIntake(recommended);
    }

    private void validateProfile(AnimalProfile profile) {
        if (profile.getName() == null || profile.getName().isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        if (profile.getWeightKg() == null || profile.getWeightKg() <= 0) {
            throw new IllegalArgumentException("El peso debe ser mayor a 0");
        }
        if (profile.getAgeMonths() == null || profile.getAgeMonths() < 0) {
            throw new IllegalArgumentException("La edad no puede ser negativa");
        }
        if (profile.getBodyCondition() == null ||
            profile.getBodyCondition() < 1 || profile.getBodyCondition() > 5) {
            throw new IllegalArgumentException("La condición corporal debe ser entre 1 y 5");
        }

        if (profile.getLifeStage() != null) {
            switch (profile.getLifeStage()) {
                case LACTATING -> {
                    if (profile.getPuppyCount() == null || profile.getPuppyCount() <= 0) {
                        throw new IllegalArgumentException(
                            "En lactancia, el número de cachorros es obligatorio");
                    }
                    if (profile.getLactationWeeks() == null ||
                        profile.getLactationWeeks() < 1 || profile.getLactationWeeks() > 4) {
                        throw new IllegalArgumentException(
                            "Las semanas de lactancia deben ser entre 1 y 4");
                    }
                }
                case PUPPY -> {
                    if (profile.getAdultWeightPuppy() == null || profile.getAdultWeightPuppy() <= 0) {
                        throw new IllegalArgumentException(
                            "Para cachorros, el peso de adulto es obligatorio");
                    }
                }
                default -> { }
            }
        }
    }
}
