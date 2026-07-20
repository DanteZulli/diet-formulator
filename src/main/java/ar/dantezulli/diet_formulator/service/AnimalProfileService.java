package ar.dantezulli.diet_formulator.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.dantezulli.diet_formulator.model.entities.AnimalProfile;
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
    public Optional<AnimalProfile> findById(UUID id) {
        return repository.findById(id);
    }

    public AnimalProfile save(AnimalProfile profile) {
        double recommended = energyCalculator.calculateRecommendedIntake(profile);
        profile.setRecommendedCaloricIntake(recommended);
        return repository.save(profile);
    }

    public void deleteById(UUID id) {
        AnimalProfile profile = repository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Perfil no encontrado: " + id));
        profile.getDiets().size();
        repository.delete(profile);
    }
}
