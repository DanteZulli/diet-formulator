package ar.dantezulli.diet_formulator.model.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import ar.dantezulli.diet_formulator.model.enums.Species;
import ar.dantezulli.diet_formulator.model.enums.LifeStage;
import ar.dantezulli.diet_formulator.model.enums.ActivityLevel;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "animal_profile")
public class AnimalProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "La especie es obligatoria")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Species species;

    @NotNull(message = "La etapa de vida es obligatoria")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LifeStage lifeStage;

    @NotNull(message = "El peso es obligatorio")
    @Min(value = 1, message = "El peso debe ser mayor a 0")
    @Column(nullable = false)
    private Double weightKg;

    private Double idealWeightKg;

    @Column(nullable = false)
    private Boolean atIdealWeight = true;

    @NotNull(message = "La edad es obligatoria")
    @Min(value = 0, message = "La edad no puede ser negativa")
    @Column(nullable = false)
    private Integer ageMonths;

    private String breed;

    @NotNull(message = "La condición corporal es obligatoria")
    @Min(value = 1, message = "La condición corporal debe ser entre 1 y 5")
    @Max(value = 5, message = "La condición corporal debe ser entre 1 y 5")
    @Column(nullable = false)
    private Integer bodyCondition;

    @Column(nullable = false)
    private Boolean blackCoat = false;

    @NotNull(message = "El nivel de actividad es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActivityLevel activityLevel;

    private Double adultWeightPuppy;

    @Column(nullable = false)
    private Boolean overweight = false;

    private Integer puppyCount;

    private Integer lactationWeeks;

    private Double customCaloricIntake;

    @Column(nullable = false)
    private Boolean useRecommendedIntake = true;

    @Column(nullable = false)
    private Double recommendedCaloricIntake;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @ToString.Exclude
    @OneToMany(mappedBy = "animalProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Diet> diets = new ArrayList<>();

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
