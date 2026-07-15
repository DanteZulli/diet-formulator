package ar.dantezulli.diet_formulator.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ar.dantezulli.diet_formulator.model.AnimalProfile;
import ar.dantezulli.diet_formulator.model.Diet;
import ar.dantezulli.diet_formulator.model.Food;
import ar.dantezulli.diet_formulator.model.enums.Nutrient;
import ar.dantezulli.diet_formulator.model.enums.UnidadCantidad;
import ar.dantezulli.diet_formulator.service.AnimalProfileService;
import ar.dantezulli.diet_formulator.service.DietService;
import ar.dantezulli.diet_formulator.service.FoodService;

/**
 * Controller for diet management.
 * Controlador para gestión de dietas.
 */
@Controller
@RequestMapping("/diets")
public class DietController {

    private final DietService dietService;
    private final AnimalProfileService profileService;
    private final FoodService foodService;

    public DietController(DietService dietService, AnimalProfileService profileService, FoodService foodService) {
        this.dietService = dietService;
        this.profileService = profileService;
        this.foodService = foodService;
    }

    /**
     * Lists all diets. / Lista todas las dietas.
     */
    @GetMapping
    public String list(Model model) {
        List<Diet> diets = dietService.findAll();
        model.addAttribute("diets", diets);
        return "diets/list";
    }

    /**
     * Shows the create diet form. / Muestra el formulario de creación de dieta.
     */
    @GetMapping("/new")
    public String createForm(@RequestParam(required = false) Long profileId, Model model) {
        Diet diet = new Diet();
        if (profileId != null) {
            AnimalProfile profile = profileService.findById(profileId)
                .orElseThrow(() -> new IllegalArgumentException("Perfil no encontrado / Profile not found"));
            diet.setAnimalProfile(profile);
        }
        model.addAttribute("diet", diet);
        model.addAttribute("profiles", profileService.findAll());
        model.addAttribute("isNew", true);
        return "diets/form";
    }

    /**
     * Saves a new diet. / Guarda una nueva dieta.
     */
    @PostMapping
    public String save(Diet diet) {
        if (diet.getAnimalProfile() == null || diet.getAnimalProfile().getId() == null) {
            return "redirect:/diets/new";
        }
        AnimalProfile profile = profileService.findById(diet.getAnimalProfile().getId())
            .orElseThrow(() -> new IllegalArgumentException("Perfil no encontrado"));
        diet.setAnimalProfile(profile);
        dietService.save(diet);
        return "redirect:/diets/" + diet.getId();
    }

    /**
     * Shows a diet with its nutrient table. / Muestra una dieta con su tabla de nutrientes.
     */
    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model) {
        Diet diet = dietService.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Dieta no encontrada / Diet not found: " + id));

        Map<Nutrient, DietService.NutrientSummary> summary = dietService.calculateNutrientSummary(diet);
        List<Food> allFoods = foodService.findAll();

        model.addAttribute("diet", diet);
        model.addAttribute("summary", summary);
        model.addAttribute("allFoods", allFoods);
        model.addAttribute("unidadesCantidad", UnidadCantidad.values());
        model.addAttribute("nutrients", Nutrient.values());
        return "diets/view";
    }

    /**
     * Adds a food item to a diet. / Agrega un ítem de alimento a una dieta.
     */
    @PostMapping("/{id}/items")
    public String addItem(@PathVariable Long id,
                          @RequestParam Long foodId,
                          @RequestParam Double cantidad,
                          @RequestParam UnidadCantidad unidad,
                          @RequestParam(required = false) String tipoCoccion) {
        dietService.addItem(id, foodId, cantidad, unidad, tipoCoccion);
        return "redirect:/diets/" + id;
    }

    /**
     * Removes a food item from a diet. / Elimina un ítem de alimento de una dieta.
     */
    @PostMapping("/{dietId}/items/{itemId}/delete")
    public String removeItem(@PathVariable Long dietId, @PathVariable Long itemId) {
        dietService.removeItem(dietId, itemId);
        return "redirect:/diets/" + dietId;
    }

    /**
     * Deletes a diet. / Elimina una dieta.
     */
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        dietService.deleteById(id);
        return "redirect:/diets";
    }
}
