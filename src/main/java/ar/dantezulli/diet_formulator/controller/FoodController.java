package ar.dantezulli.diet_formulator.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ar.dantezulli.diet_formulator.model.Food;
import ar.dantezulli.diet_formulator.model.enums.TipoAlimento;
import ar.dantezulli.diet_formulator.model.enums.UnidadPorcion;
import ar.dantezulli.diet_formulator.service.FoodService;

/**
 * Controller for food management.
 * Controlador para gestión de alimentos.
 */
@Controller
@RequestMapping("/foods")
public class FoodController {

    private final FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    /**
     * Lists all foods with optional search. / Lista todos los alimentos con búsqueda opcional.
     */
    @GetMapping
    public String list(@RequestParam(required = false) String search, Model model) {
        List<Food> foods;
        if (search != null && !search.isBlank()) {
            foods = foodService.search(search);
            model.addAttribute("search", search);
        } else {
            foods = foodService.findAll();
        }
        model.addAttribute("foods", foods);
        return "foods/list";
    }

    /**
     * Shows the create food form. / Muestra el formulario de creación de alimento.
     */
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("food", new Food());
        addEnumOptions(model);
        return "foods/form";
    }

    /**
     * Shows the edit food form. / Muestra el formulario de edición de alimento.
     */
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Food food = foodService.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Alimento no encontrado / Food not found: " + id));
        model.addAttribute("food", food);
        addEnumOptions(model);
        return "foods/form";
    }

    /**
     * Saves a food (create or update). / Guarda un alimento (crear o actualizar).
     */
    @PostMapping
    public String save(@ModelAttribute Food food) {
        foodService.save(food);
        return "redirect:/foods";
    }

    /**
     * Deletes a food. / Elimina un alimento.
     */
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        foodService.deleteById(id);
        return "redirect:/foods";
    }

    private void addEnumOptions(Model model) {
        model.addAttribute("tiposAlimento", TipoAlimento.values());
        model.addAttribute("unidadesPorcion", UnidadPorcion.values());
    }
}
