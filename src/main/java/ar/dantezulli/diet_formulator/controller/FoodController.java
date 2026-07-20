package ar.dantezulli.diet_formulator.controller;

import java.beans.PropertyEditorSupport;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ar.dantezulli.diet_formulator.model.entities.Food;
import ar.dantezulli.diet_formulator.model.enums.FoodType;
import ar.dantezulli.diet_formulator.model.enums.Nutrient;
import ar.dantezulli.diet_formulator.model.enums.NutrientCategory;
import ar.dantezulli.diet_formulator.model.enums.PortionUnit;
import ar.dantezulli.diet_formulator.service.FoodService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/foods")
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Double.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                if (text == null || text.isBlank()) {
                    setValue(null);
                } else {
                    setValue(Double.parseDouble(text));
                }
            }
        });
    }

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

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("food", new Food());
        addEnumOptions(model);
        return "foods/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable UUID id, Model model) {
        Food food = foodService.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Alimento no encontrado: " + id));
        model.addAttribute("food", food);
        addEnumOptions(model);
        return "foods/form";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute Food food, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("food", food);
            addEnumOptions(model);
            return "foods/form";
        }

        try {
            foodService.save(food);
            redirectAttributes.addFlashAttribute("success", "Alimento guardado correctamente");
        } catch (IllegalArgumentException e) {
            model.addAttribute("food", food);
            model.addAttribute("error", e.getMessage());
            addEnumOptions(model);
            return "foods/form";
        }

        return "redirect:/foods";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable UUID id, RedirectAttributes redirectAttributes) {
        foodService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Alimento eliminado correctamente");
        return "redirect:/foods";
    }

    private void addEnumOptions(Model model) {
        model.addAttribute("foodTypes", FoodType.values());
        model.addAttribute("portionUnits", PortionUnit.values());
        addNutrientOptions(model);
    }

    private void addNutrientOptions(Model model) {
        Map<NutrientCategory, List<Nutrient>> nutrientsByCategory = new LinkedHashMap<>();

        for (NutrientCategory category : NutrientCategory.values()) {
            nutrientsByCategory.put(category, Arrays.stream(Nutrient.values())
                .filter(n -> n.getCategory() == category)
                .collect(Collectors.toList()));
        }

        List<Nutrient> uncategorized = Arrays.stream(Nutrient.values())
            .filter(n -> n.getCategory() == null)
            .collect(Collectors.toList());

        model.addAttribute("nutrientsByCategory", nutrientsByCategory);
        model.addAttribute("uncategorizedNutrients", uncategorized);
    }
}
