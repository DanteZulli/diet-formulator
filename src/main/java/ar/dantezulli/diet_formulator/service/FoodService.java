package ar.dantezulli.diet_formulator.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.dantezulli.diet_formulator.model.Food;
import ar.dantezulli.diet_formulator.model.enums.FoodType;
import ar.dantezulli.diet_formulator.repository.FoodRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class FoodService {

    private final FoodRepository repository;

    @Transactional(readOnly = true)
    public List<Food> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Food> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Food> findByType(FoodType type) {
        return repository.findByType(type);
    }

    @Transactional(readOnly = true)
    public List<Food> search(String query) {
        return repository.findByNameContainingIgnoreCase(query);
    }

    public Food save(Food food) {
        return repository.save(food);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
