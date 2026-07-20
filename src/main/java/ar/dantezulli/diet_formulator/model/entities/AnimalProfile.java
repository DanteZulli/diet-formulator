package ar.dantezulli.diet_formulator.model.entities;

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
import jakarta.persistence.Table;
import ar.dantezulli.diet_formulator.model.enums.Species;
import ar.dantezulli.diet_formulator.model.enums.LifeStage;
import ar.dantezulli.diet_formulator.model.enums.ActivityLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "animal_profile")
public class AnimalProfile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

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

    @Column(columnDefinition = "TEXT")
    private String notes;

    @ToString.Exclude
    @OneToMany(mappedBy = "animalProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Diet> diets = new ArrayList<>();
}
