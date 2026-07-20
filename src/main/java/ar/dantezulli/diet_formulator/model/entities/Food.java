package ar.dantezulli.diet_formulator.model.entities;

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
import jakarta.persistence.Table;
import ar.dantezulli.diet_formulator.model.enums.Nutrient;
import ar.dantezulli.diet_formulator.model.enums.FoodType;
import ar.dantezulli.diet_formulator.model.enums.PortionUnit;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "food")
public class Food extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

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
}
