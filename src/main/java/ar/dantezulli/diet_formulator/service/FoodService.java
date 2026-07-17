package ar.dantezulli.diet_formulator.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.dantezulli.diet_formulator.model.Food;
import ar.dantezulli.diet_formulator.model.enums.TipoAlimento;
import ar.dantezulli.diet_formulator.repository.FoodRepository;
import lombok.RequiredArgsConstructor;

/**
 * Service for Food CRUD operations.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class FoodService {

    private final FoodRepository repository;

    /**
     * Returns all foods.
     */
    @Transactional(readOnly = true)
    public List<Food> findAll() {
        return repository.findAll();
    }

    /**
     * Finds a food by ID.
     */
    @Transactional(readOnly = true)
    public Optional<Food> findById(Long id) {
        return repository.findById(id);
    }

    /**
     * Finds foods by type.
     */
    @Transactional(readOnly = true)
    public List<Food> findByTipo(TipoAlimento tipo) {
        return repository.findByTipo(tipo);
    }

    /**
     * Searches foods by name.
     */
    @Transactional(readOnly = true)
    public List<Food> search(String query) {
        return repository.findByNombreContainingIgnoreCase(query);
    }

    /**
     * Saves a food.
     */
    public Food save(Food food) {
        return repository.save(food);
    }

    /**
     * Deletes a food by ID.
     */
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
