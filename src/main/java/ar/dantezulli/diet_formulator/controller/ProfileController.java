package ar.dantezulli.diet_formulator.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ar.dantezulli.diet_formulator.model.AnimalProfile;
import ar.dantezulli.diet_formulator.model.MacronutrientTargets;
import ar.dantezulli.diet_formulator.model.enums.Especie;
import ar.dantezulli.diet_formulator.model.enums.LifeStage;
import ar.dantezulli.diet_formulator.model.enums.NivelActividad;
import ar.dantezulli.diet_formulator.service.AnimalProfileService;
import lombok.RequiredArgsConstructor;

/**
 * Controller for animal profile management.
 */
@Controller
@RequestMapping("/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final AnimalProfileService profileService;

    /**
     * Lists all profiles.
     */
    @GetMapping
    public String list(Model model) {
        List<AnimalProfile> profiles = profileService.findAll();
        model.addAttribute("profiles", profiles);
        return "profiles/list";
    }

    /**
     * Shows the create profile form.
     */
    @GetMapping("/new")
    public String createForm(Model model) {
        AnimalProfile profile = new AnimalProfile();
        profile.setMacronutrientTargets(new MacronutrientTargets(0.0, 0.0, 0.0, 0.0));
        model.addAttribute("profile", profile);
        addEnumOptions(model);
        return "profiles/form";
    }

    /**
     * Shows the edit profile form.
     */
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        AnimalProfile profile = profileService.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Perfil no encontrado / Profile not found: " + id));
        model.addAttribute("profile", profile);
        addEnumOptions(model);
        return "profiles/form";
    }

    /**
     * Saves a profile (create or update).
     */
    @PostMapping
    public String save(AnimalProfile profile, BindingResult result, Model model) {
        if (result.hasErrors()) {
            addEnumOptions(model);
            return "profiles/form";
        }

        try {
            profileService.save(profile);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            addEnumOptions(model);
            return "profiles/form";
        }

        return "redirect:/profiles";
    }

    /**
     * Deletes a profile.
     */
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        profileService.deleteById(id);
        return "redirect:/profiles";
    }

    private void addEnumOptions(Model model) {
        model.addAttribute("especies", Especie.values());
        model.addAttribute("lifeStages", LifeStage.values());
        model.addAttribute("nivelesActividad", NivelActividad.values());
    }
}
