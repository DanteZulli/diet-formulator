package ar.dantezulli.diet_formulator.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import ar.dantezulli.diet_formulator.model.enums.Species;
import ar.dantezulli.diet_formulator.model.enums.LifeStage;
import ar.dantezulli.diet_formulator.model.enums.ActivityLevel;
import lombok.Data;

@Data
@Entity
@Table(name = "animal_profile")
public class AnimalProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Species species;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LifeStage lifeStage;

    @Column(nullable = false)
    private Double weightKg;

    private Double idealWeightKg;

    @Column(nullable = false)
    private Boolean atIdealWeight = true;

    @Column(nullable = false)
    private Integer ageMonths;

    private String breed;

    @Column(nullable = false)
    private Integer bodyCondition;

    @Column(nullable = false)
    private Boolean blackCoat = false;

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

    @Embedded
    private MacronutrientTargets macronutrientTargets;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @jakarta.persistence.PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @jakarta.persistence.PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
