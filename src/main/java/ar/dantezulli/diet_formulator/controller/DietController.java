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
import ar.dantezulli.diet_formulator.model.enums.QuantityUnit;
import ar.dantezulli.diet_formulator.service.AnimalProfileService;
import ar.dantezulli.diet_formulator.service.DietService;
import ar.dantezulli.diet_formulator.service.FoodService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/diets")
@RequiredArgsConstructor
public class DietController {

    private final DietService dietService;
    private final AnimalProfileService profileService;
    private final FoodService foodService;

    @GetMapping
    public String list(Model model) {
        List<Diet> diets = dietService.findAll();
        model.addAttribute("diets", diets);
        return "diets/list";
    }

    @GetMapping("/new")
    public String createForm(@RequestParam(required = false) Long profileId, Model model) {
        Diet diet = new Diet();
        if (profileId != null) {
            AnimalProfile profile = profileService.findById(profileId)
                .orElseThrow(() -> new IllegalArgumentException("Perfil no encontrado"));
            diet.setAnimalProfile(profile);
        }
        model.addAttribute("diet", diet);
        model.addAttribute("profiles", profileService.findAll());
        model.addAttribute("isNew", true);
        return "diets/form";
    }

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

    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model) {
        Diet diet = dietService.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Dieta no encontrada: " + id));

        Map<Nutrient, DietService.NutrientSummary> summary = dietService.calculateNutrientSummary(diet);
        List<Food> allFoods = foodService.findAll();

        model.addAttribute("diet", diet);
        model.addAttribute("summary", summary);
        model.addAttribute("allFoods", allFoods);
        model.addAttribute("quantityUnits", QuantityUnit.values());
        model.addAttribute("nutrients", Nutrient.values());
        return "diets/view";
    }

    @PostMapping("/{id}/items")
    public String addItem(@PathVariable Long id,
                          @RequestParam Long foodId,
                          @RequestParam Double quantity,
                          @RequestParam QuantityUnit unit,
                          @RequestParam(required = false) String cookingMethod) {
        dietService.addItem(id, foodId, quantity, unit, cookingMethod);
        return "redirect:/diets/" + id;
    }

    @PostMapping("/{dietId}/items/{itemId}/delete")
    public String removeItem(@PathVariable Long dietId, @PathVariable Long itemId) {
        dietService.removeItem(dietId, itemId);
        return "redirect:/diets/" + dietId;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        dietService.deleteById(id);
        return "redirect:/diets";
    }
}
