package ar.dantezulli.diet_formulator.model.dto;

import java.util.UUID;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import ar.dantezulli.diet_formulator.model.entities.AnimalProfile;
import ar.dantezulli.diet_formulator.model.enums.ActivityLevel;
import ar.dantezulli.diet_formulator.model.enums.LifeStage;
import ar.dantezulli.diet_formulator.model.enums.Species;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimalProfileDTO {

    private UUID id;

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @NotNull(message = "La especie es obligatoria")
    private Species species;

    @NotNull(message = "La etapa de vida es obligatoria")
    private LifeStage lifeStage;

    @NotNull(message = "El peso es obligatorio")
    @Min(value = 1, message = "El peso debe ser mayor a 0")
    private Double weightKg;

    private Double idealWeightKg;

    private Boolean atIdealWeight = true;

    @NotNull(message = "La edad es obligatoria")
    @Min(value = 0, message = "La edad no puede ser negativa")
    private Integer ageMonths;

    private String breed;

    @NotNull(message = "La condición corporal es obligatoria")
    @Min(value = 1, message = "La condición corporal debe ser entre 1 y 5")
    @Max(value = 5, message = "La condición corporal debe ser entre 1 y 5")
    private Integer bodyCondition;

    private Boolean blackCoat = false;

    @NotNull(message = "El nivel de actividad es obligatorio")
    private ActivityLevel activityLevel;

    private Double adultWeightPuppy;

    private Boolean overweight = false;

    private Integer puppyCount;

    private Integer lactationWeeks;

    private Double customCaloricIntake;

    private Boolean useRecommendedIntake = true;

    private Double recommendedCaloricIntake;

    private String notes;

    public AnimalProfile toEntity() {
        AnimalProfile entity = new AnimalProfile();
        entity.setId(id);
        entity.setName(name);
        entity.setSpecies(species);
        entity.setLifeStage(lifeStage);
        entity.setWeightKg(weightKg);
        entity.setIdealWeightKg(idealWeightKg);
        entity.setAtIdealWeight(atIdealWeight);
        entity.setAgeMonths(ageMonths);
        entity.setBreed(breed);
        entity.setBodyCondition(bodyCondition);
        entity.setBlackCoat(blackCoat);
        entity.setActivityLevel(activityLevel);
        entity.setAdultWeightPuppy(adultWeightPuppy);
        entity.setOverweight(overweight);
        entity.setPuppyCount(puppyCount);
        entity.setLactationWeeks(lactationWeeks);
        entity.setCustomCaloricIntake(customCaloricIntake);
        entity.setUseRecommendedIntake(useRecommendedIntake);
        entity.setRecommendedCaloricIntake(recommendedCaloricIntake);
        entity.setNotes(notes);
        return entity;
    }

    public static AnimalProfileDTO from(AnimalProfile entity) {
        AnimalProfileDTO dto = new AnimalProfileDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSpecies(entity.getSpecies());
        dto.setLifeStage(entity.getLifeStage());
        dto.setWeightKg(entity.getWeightKg());
        dto.setIdealWeightKg(entity.getIdealWeightKg());
        dto.setAtIdealWeight(entity.getAtIdealWeight());
        dto.setAgeMonths(entity.getAgeMonths());
        dto.setBreed(entity.getBreed());
        dto.setBodyCondition(entity.getBodyCondition());
        dto.setBlackCoat(entity.getBlackCoat());
        dto.setActivityLevel(entity.getActivityLevel());
        dto.setAdultWeightPuppy(entity.getAdultWeightPuppy());
        dto.setOverweight(entity.getOverweight());
        dto.setPuppyCount(entity.getPuppyCount());
        dto.setLactationWeeks(entity.getLactationWeeks());
        dto.setCustomCaloricIntake(entity.getCustomCaloricIntake());
        dto.setUseRecommendedIntake(entity.getUseRecommendedIntake());
        dto.setRecommendedCaloricIntake(entity.getRecommendedCaloricIntake());
        dto.setNotes(entity.getNotes());
        return dto;
    }
}
