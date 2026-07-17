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
import ar.dantezulli.diet_formulator.model.enums.FoodType;
import ar.dantezulli.diet_formulator.model.enums.PortionUnit;
import ar.dantezulli.diet_formulator.service.FoodService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/foods")
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;

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
    public String editForm(@PathVariable Long id, Model model) {
        Food food = foodService.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Alimento no encontrado: " + id));
        model.addAttribute("food", food);
        addEnumOptions(model);
        return "foods/form";
    }

    @PostMapping
    public String save(@ModelAttribute Food food) {
        foodService.save(food);
        return "redirect:/foods";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        foodService.deleteById(id);
        return "redirect:/foods";
    }

    private void addEnumOptions(Model model) {
        model.addAttribute("foodTypes", FoodType.values());
        model.addAttribute("portionUnits", PortionUnit.values());
    }
}
