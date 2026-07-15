package ar.dantezulli.diet_formulator.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.dantezulli.diet_formulator.model.Food;
import ar.dantezulli.diet_formulator.model.enums.TipoAlimento;
import ar.dantezulli.diet_formulator.repository.FoodRepository;

/**
 * Service for Food CRUD operations.
 * Servicio para operaciones CRUD de Food.
 */
@Service
@Transactional
public class FoodService {

    private final FoodRepository repository;

    public FoodService(FoodRepository repository) {
        this.repository = repository;
    }

    /**
     * Returns all foods. / Retorna todos los alimentos.
     */
    @Transactional(readOnly = true)
    public List<Food> findAll() {
        return repository.findAll();
    }

    /**
     * Finds a food by ID. / Busca un alimento por ID.
     */
    @Transactional(readOnly = true)
    public Optional<Food> findById(Long id) {
        return repository.findById(id);
    }

    /**
     * Finds foods by type. / Busca alimentos por tipo.
     */
    @Transactional(readOnly = true)
    public List<Food> findByTipo(TipoAlimento tipo) {
        return repository.findByTipo(tipo);
    }

    /**
     * Searches foods by name. / Busca alimentos por nombre.
     */
    @Transactional(readOnly = true)
    public List<Food> search(String query) {
        return repository.findByNombreContainingIgnoreCase(query);
    }

    /**
     * Saves a food. / Guarda un alimento.
     */
    public Food save(Food food) {
        return repository.save(food);
    }

    /**
     * Deletes a food by ID. / Elimina un alimento por ID.
     */
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
