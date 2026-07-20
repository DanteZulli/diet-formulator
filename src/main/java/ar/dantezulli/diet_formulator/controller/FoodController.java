package ar.dantezulli.diet_formulator.controller;

import java.beans.PropertyEditorSupport;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ar.dantezulli.diet_formulator.model.dto.FoodDTO;
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

    private static final String VIEW_LIST = "foods/list";
    private static final String VIEW_FORM = "foods/form";

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
    public ModelAndView list(@RequestParam(required = false) String search) {
        ModelAndView mav = new ModelAndView(VIEW_LIST);
        if (search != null && !search.isBlank()) {
            mav.addObject("foods", foodService.search(search));
            mav.addObject("search", search);
        } else {
            mav.addObject("foods", foodService.findAll());
        }
        return mav;
    }

    @GetMapping("/new")
    public ModelAndView createForm() {
        ModelAndView mav = new ModelAndView(VIEW_FORM);
        mav.addObject("food", new FoodDTO());
        addEnumOptions(mav);
        return mav;
    }

    @GetMapping("/{id}/edit")
    public ModelAndView editForm(@PathVariable java.util.UUID id) {
        Food food = foodService.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Alimento no encontrado: " + id));
        ModelAndView mav = new ModelAndView(VIEW_FORM);
        mav.addObject("food", FoodDTO.from(food));
        addEnumOptions(mav);
        return mav;
    }

    @PostMapping
    public String save(@Valid @ModelAttribute FoodDTO foodDTO, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("food", foodDTO);
            addEnumOptions(model);
            return VIEW_FORM;
        }

        try {
            foodService.save(foodDTO.toEntity());
            redirectAttributes.addFlashAttribute("success", "Alimento guardado correctamente");
        } catch (IllegalArgumentException e) {
            model.addAttribute("food", foodDTO);
            model.addAttribute("error", e.getMessage());
            addEnumOptions(model);
            return VIEW_FORM;
        }

        return "redirect:/foods";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable java.util.UUID id, RedirectAttributes redirectAttributes) {
        foodService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Alimento eliminado correctamente");
        return "redirect:/foods";
    }

    private void addEnumOptions(ModelAndView mav) {
        Map<String, Object> options = buildFoodOptions();
        mav.addAllObjects(options);
    }

    private void addEnumOptions(Model model) {
        buildFoodOptions().forEach(model::addAttribute);
    }

    private Map<String, Object> buildFoodOptions() {
        Map<String, Object> options = new LinkedHashMap<>();
        options.put("foodTypes", FoodType.values());
        options.put("portionUnits", PortionUnit.values());
        options.putAll(buildNutrientOptions());
        return options;
    }

    private Map<String, Object> buildNutrientOptions() {
        Map<NutrientCategory, List<Nutrient>> nutrientsByCategory = new LinkedHashMap<>();

        for (NutrientCategory category : NutrientCategory.values()) {
            nutrientsByCategory.put(category, Arrays.stream(Nutrient.values())
                .filter(n -> n.getCategory() == category)
                .collect(Collectors.toList()));
        }

        List<Nutrient> uncategorized = Arrays.stream(Nutrient.values())
            .filter(n -> n.getCategory() == null)
            .collect(Collectors.toList());

        Map<String, Object> options = new LinkedHashMap<>();
        options.put("nutrientsByCategory", nutrientsByCategory);
        options.put("uncategorizedNutrients", uncategorized);
        return options;
    }
}
