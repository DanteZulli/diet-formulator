package ar.dantezulli.diet_formulator.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ar.dantezulli.diet_formulator.model.entities.AnimalProfile;
import ar.dantezulli.diet_formulator.model.enums.Species;
import ar.dantezulli.diet_formulator.model.enums.LifeStage;
import ar.dantezulli.diet_formulator.model.enums.ActivityLevel;
import ar.dantezulli.diet_formulator.service.AnimalProfileService;
import ar.dantezulli.diet_formulator.service.EnergyCalculator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final AnimalProfileService profileService;
    private final EnergyCalculator energyCalculator;

    @GetMapping
    public String list(Model model) {
        List<AnimalProfile> profiles = profileService.findAll();
        model.addAttribute("profiles", profiles);
        return "profiles/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        AnimalProfile profile = new AnimalProfile();
        model.addAttribute("profile", profile);
        addEnumOptions(model);
        return "profiles/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable UUID id, Model model) {
        AnimalProfile profile = profileService.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Perfil no encontrado: " + id));
        model.addAttribute("profile", profile);
        addEnumOptions(model);
        return "profiles/form";
    }

    @PostMapping
    public String save(@Valid AnimalProfile profile, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("profile", profile);
            addEnumOptions(model);
            return "profiles/form";
        }

        try {
            profileService.save(profile);
            redirectAttributes.addFlashAttribute("success", "Perfil guardado correctamente");
        } catch (IllegalArgumentException e) {
            model.addAttribute("profile", profile);
            model.addAttribute("error", e.getMessage());
            addEnumOptions(model);
            return "profiles/form";
        }

        return "redirect:/profiles";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable UUID id, RedirectAttributes redirectAttributes) {
        profileService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Perfil eliminado correctamente");
        return "redirect:/profiles";
    }

    @GetMapping("/api/calculate-intake")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> calculateIntake(
            @RequestParam Species species,
            @RequestParam LifeStage lifeStage,
            @RequestParam Double weightKg,
            @RequestParam Integer ageMonths,
            @RequestParam ActivityLevel activityLevel,
            @RequestParam(defaultValue = "true") Boolean atIdealWeight,
            @RequestParam(required = false) Double idealWeightKg,
            @RequestParam(required = false) Integer puppyCount,
            @RequestParam(required = false) Integer lactationWeeks) {

        AnimalProfile profile = new AnimalProfile();
        profile.setSpecies(species);
        profile.setLifeStage(lifeStage);
        profile.setWeightKg(weightKg);
        profile.setAgeMonths(ageMonths);
        profile.setActivityLevel(activityLevel);
        profile.setAtIdealWeight(atIdealWeight);
        profile.setIdealWeightKg(idealWeightKg);
        profile.setPuppyCount(puppyCount);
        profile.setLactationWeeks(lactationWeeks);

        try {
            double intake = energyCalculator.calculateRecommendedIntake(profile);
            return ResponseEntity.ok(Map.of("recommendedIntake", intake));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    private void addEnumOptions(Model model) {
        model.addAttribute("species", Species.values());
        model.addAttribute("lifeStages", LifeStage.values());
        model.addAttribute("activityLevels", ActivityLevel.values());
    }
}
