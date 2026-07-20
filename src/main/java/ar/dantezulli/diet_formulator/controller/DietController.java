package ar.dantezulli.diet_formulator.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ar.dantezulli.diet_formulator.model.dto.DietDTO;
import ar.dantezulli.diet_formulator.model.dto.DietItemDTO;
import ar.dantezulli.diet_formulator.model.entities.AnimalProfile;
import ar.dantezulli.diet_formulator.model.entities.Diet;
import ar.dantezulli.diet_formulator.model.entities.Food;
import ar.dantezulli.diet_formulator.model.enums.Nutrient;
import ar.dantezulli.diet_formulator.model.enums.QuantityUnit;
import ar.dantezulli.diet_formulator.service.AnimalProfileService;
import ar.dantezulli.diet_formulator.service.DietService;
import ar.dantezulli.diet_formulator.service.FoodService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/diets")
@RequiredArgsConstructor
public class DietController {

    private static final String VIEW_LIST = "diets/list";
    private static final String VIEW_FORM = "diets/form";
    private static final String VIEW_VIEW = "diets/view";

    private final DietService dietService;
    private final AnimalProfileService profileService;
    private final FoodService foodService;

    @GetMapping
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView(VIEW_LIST);
        mav.addObject("diets", dietService.findAll());
        return mav;
    }

    @GetMapping("/new")
    public ModelAndView createForm(@RequestParam(required = false) UUID profileId) {
        DietDTO dto = new DietDTO();
        if (profileId != null) {
            dto.setAnimalProfileId(profileId);
        }
        ModelAndView mav = new ModelAndView(VIEW_FORM);
        mav.addObject("diet", dto);
        mav.addObject("profiles", profileService.findAll());
        mav.addObject("isNew", true);
        return mav;
    }

    @PostMapping
    public String save(@Valid DietDTO dietDTO, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("diet", dietDTO);
            model.addAttribute("profiles", profileService.findAll());
            model.addAttribute("isNew", dietDTO.getId() == null);
            return VIEW_FORM;
        }

        if (dietDTO.getAnimalProfileId() == null) {
            model.addAttribute("error", "Debe seleccionar un perfil");
            model.addAttribute("diet", dietDTO);
            model.addAttribute("profiles", profileService.findAll());
            model.addAttribute("isNew", dietDTO.getId() == null);
            return VIEW_FORM;
        }
        AnimalProfile profile = profileService.findById(dietDTO.getAnimalProfileId())
            .orElseThrow(() -> new IllegalArgumentException("Perfil no encontrado"));
        Diet diet = dietDTO.toEntity();
        diet.setAnimalProfile(profile);
        dietService.save(diet);
        redirectAttributes.addFlashAttribute("success", "Dieta guardada correctamente");
        return "redirect:/diets/" + diet.getId();
    }

    @GetMapping("/{id}")
    public ModelAndView view(@PathVariable UUID id) {
        Diet diet = dietService.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Dieta no encontrada: " + id));

        Map<Nutrient, DietService.NutrientSummary> summary = dietService.calculateNutrientSummary(diet);
        List<Food> allFoods = foodService.findAll();

        ModelAndView mav = new ModelAndView(VIEW_VIEW);
        mav.addObject("diet", diet);
        mav.addObject("summary", summary);
        mav.addObject("allFoods", allFoods);
        mav.addObject("quantityUnits", QuantityUnit.values());
        mav.addObject("nutrients", Nutrient.values());
        return mav;
    }

    @PostMapping("/{id}/items")
    public String addItem(@PathVariable UUID id,
                          @Valid DietItemDTO itemDTO,
                          BindingResult result,
                          RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Datos del alimento inválidos");
            return "redirect:/diets/" + id;
        }
        dietService.addItem(id, itemDTO.getFoodId(), itemDTO.getQuantity(), itemDTO.getUnit(), itemDTO.getCookingMethod());
        redirectAttributes.addFlashAttribute("success", "Alimento agregado correctamente");
        return "redirect:/diets/" + id;
    }

    @PostMapping("/{dietId}/items/{itemId}/delete")
    public String removeItem(@PathVariable UUID dietId, @PathVariable UUID itemId, RedirectAttributes redirectAttributes) {
        dietService.removeItem(dietId, itemId);
        redirectAttributes.addFlashAttribute("success", "Alimento eliminado de la dieta");
        return "redirect:/diets/" + dietId;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable UUID id, RedirectAttributes redirectAttributes) {
        dietService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Dieta eliminada correctamente");
        return "redirect:/diets";
    }
}
