package ar.dantezulli.diet_formulator.model.entities;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import ar.dantezulli.diet_formulator.model.enums.Nutrient;
import ar.dantezulli.diet_formulator.model.enums.FoodType;
import ar.dantezulli.diet_formulator.model.enums.PortionUnit;
import lombok.Data;

@Data
@Entity
@Table(name = "food")
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "El tipo es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FoodType type;

    private Double portion;

    @Enumerated(EnumType.STRING)
    private PortionUnit portionUnit;

    @ElementCollection
    @CollectionTable(name = "food_nutrient", joinColumns = @jakarta.persistence.JoinColumn(name = "food_id"))
    @MapKeyColumn(name = "nutrient")
    @Column(name = "nutrient_value")
    @Enumerated(EnumType.STRING)
    private Map<Nutrient, Double> nutrients = new HashMap<>();

    private Long usdaId;

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

    public Double getNutrientValue(Nutrient nutrient) {
        return nutrients.getOrDefault(nutrient, 0.0);
    }
}
